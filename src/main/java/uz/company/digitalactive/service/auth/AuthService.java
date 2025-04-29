package uz.company.digitalactive.service.auth;

import uz.company.digitalactive.dto.AuthResponseDto;
import uz.company.digitalactive.dto.LoginDto;
import uz.company.digitalactive.dto.request.user.PasswordRequestDto;
import uz.company.digitalactive.entity.User;

public interface AuthService {
  AuthResponseDto login(LoginDto loginDto);

  Boolean changePassword(User currentUser, PasswordRequestDto passwordDto);
}
