package uz.company.digitalactive.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.digitalactive.config.CurrentUser;
import uz.company.digitalactive.dto.LoginDto;
import uz.company.digitalactive.dto.auth.TokenInfoResponse;
import uz.company.digitalactive.dto.request.user.PasswordRequestDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.mapper.UserMapper;
import uz.company.digitalactive.service.auth.AuthService;
import uz.company.digitalactive.service.auth.AuthTokenService;

@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  @Autowired private AuthService authService;
  @Autowired private UserMapper userMapper;
  @Autowired private AuthTokenService authTokenService;

  //    @PostMapping("/register")
  //    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto
  // registerRequestDto){
  //        return
  // ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequestDto));
  //    }

  @GetMapping("/me")
  public ResponseEntity<UserResponseDto> getCurrentUser(@CurrentUser User user) {
    log.info(user.toString());
    return ResponseEntity.ok(userMapper.toDto(user));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenInfoResponse> login(@Valid @RequestBody LoginDto loginDto) {
    var token = authTokenService.generateToken(loginDto);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/change-password")
  public ResponseEntity<Boolean> changePassword(@RequestBody PasswordRequestDto passwordDto) {
    User currentUser = new User();
    return ResponseEntity.ok(authService.changePassword(currentUser, passwordDto));
  }
}
