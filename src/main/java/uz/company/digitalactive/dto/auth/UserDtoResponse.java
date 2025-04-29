package uz.company.digitalactive.dto.auth;

import java.util.Set;
import lombok.Builder;
import org.springframework.lang.Nullable;
import uz.company.digitalactive.exception.UnauthorizedException;

@Builder
public record UserDtoResponse(
    Long id,
    String email,
    Set<String> roles,
    @Nullable String surname,
    @Nullable String mobile,
    @Nullable Boolean isActive) {

  public static String getUserRole(UserDtoResponse currentUserData) {
    return currentUserData.roles.stream()
        .findFirst()
        .orElseThrow(() -> new UnauthorizedException("Role not found"));
  }
}
