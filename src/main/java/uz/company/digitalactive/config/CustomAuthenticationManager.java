package uz.company.digitalactive.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uz.company.digitalactive.service.auth.AuthTokenService;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

  private final AuthTokenService authTokenService;

  @Override
  public Authentication authenticate(Authentication authentication) {
    final String authToken = authentication.getCredentials().toString();
    return authTokenService.verifyAccessToken(authToken);
  }
}
