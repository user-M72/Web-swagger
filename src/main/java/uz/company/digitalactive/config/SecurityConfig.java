package uz.company.digitalactive.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.company.digitalactive.config.properties.ApplicationProperties;
import uz.company.digitalactive.config.properties.AuthenticationExceptionEntryPoint;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ApplicationProperties.class)
public class SecurityConfig {

  private final AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint;
  private final ApplicationProperties applicationProperties;
  private static final String[] PUBLIC_URLS = {
    "/api/auth/v1/login",
    "/v2/api-docs",
    "/v3/api-docs",
    "/v3/api-docs/**",
    "/swagger-resources",
    "/swagger-resources/**",
    "/configuration/ui",
    "/configuration/security",
    "/swagger-ui/**",
    "/webjars/**",
    "/swagger-ui.html"
  };
  private static final String[] ADMIN_URLS = {
    "/api/users/v1/**",
    "/api/roles/v1/**",
    "/api/types/v1/**",
    "/api/project/v1/**",
    "/api/asset/v1/**",
  };
  private static final String[] MANAGER_URLS = {"/api/project/v1/**"};
  private static final String[] USER_URLS = {"/api/types/v1/**", "/api/asset/v1/**"};

  public SecurityConfig(
      AuthenticationExceptionEntryPoint authenticationEntryPoint,
      ApplicationProperties applicationProperties) {
    this.authenticationExceptionEntryPoint = authenticationEntryPoint;
    this.applicationProperties = applicationProperties;
  }

  @Bean
  SecurityFilterChain jwtSecurityFilterChain(
      HttpSecurity http,
      JwtAuthenticationFilter jwtAuthenticationFilter,
      AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint)
      throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(ADMIN_URLS)
                    .hasAnyAuthority("ADMIN")
                    .requestMatchers(MANAGER_URLS)
                    .hasAnyAuthority("MANAGER")
                    .requestMatchers(USER_URLS)
                    .hasAnyAuthority("USER")
                    .requestMatchers(PUBLIC_URLS)
                    .permitAll())
        .exceptionHandling(
            (ex) ->
                ex.authenticationEntryPoint(authenticationExceptionEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler()))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .anonymous(AbstractHttpConfigurer::disable); // Use JWT Auth

    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(applicationProperties.rca().publicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk =
        new RSAKey.Builder(applicationProperties.rca().publicKey())
            .privateKey(applicationProperties.rca().privateKey())
            .build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }
}
