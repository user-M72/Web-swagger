package uz.company.digitalactive.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.company.digitalactive.config.properties.AuthenticationExceptionEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager authenticationManager;
    private final AuthenticationExceptionEntryPoint authEntryPoint;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(token, token);

        try {
            Authentication authResult = authenticationManager.authenticate(authentication);
            if (authResult.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authResult);
            }
        } catch (Exception ex) {
            authEntryPoint.commence(
                    request,
                    response,
                    new AuthenticationException(ex.getMessage()) {
                        @Override
                        public String getMessage() {
                            return super.getMessage();
                        }
                    });
            return;
        }

        chain.doFilter(request, response);
    }
}
