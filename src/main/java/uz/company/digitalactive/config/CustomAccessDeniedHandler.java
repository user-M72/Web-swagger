package uz.company.digitalactive.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException) {
    HashMap<String, String> parameters = new HashMap<>();

    if (request.getUserPrincipal() instanceof AccessDeniedHandler) {
      parameters.put("error", "insufficient_scope");
      parameters.put(
          "error_description",
          "The request requires higher privileges than provided by the access token.");
      parameters.put("error_uri", "https://tools.ietf.org/html/rfc6750#section-3.1");
    }

    String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
    response.addHeader("WWW-Authenticate", wwwAuthenticate);
    response.setStatus(HttpStatus.FORBIDDEN.value());
  }

  private static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
    StringBuilder wwwAuthenticate = new StringBuilder();
    wwwAuthenticate.append("Bearer");
    if (!parameters.isEmpty()) {
      wwwAuthenticate.append(' ');
      int i = 0;

      for (Iterator<Map.Entry<String, String>> var3 = parameters.entrySet().iterator();
          var3.hasNext();
          ++i) {
        Map.Entry<String, String> entry = var3.next();
        wwwAuthenticate.append(entry.getKey()).append("=\"").append(entry.getValue()).append('\"');
        if (i != parameters.size() - 1) {
          wwwAuthenticate.append(", ");
        }
      }
    }

    return wwwAuthenticate.toString();
  }
}
