package uz.company.digitalactive.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.digitalactive.dto.request.user.UserRequestDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.service.user.UserService;

@RestController
@RequestMapping("/api/users/v1")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping
  public List<UserResponseDto> get() {
    return userService.get();
  }

  @GetMapping("/{userId}")
  public UserResponseDto getById(@PathVariable("userId") UUID id) {
    return userService.getById(id);
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
    UserResponseDto created = userService.create(userRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{userId}")
  public UserResponseDto update(
      @PathVariable("userId") UUID id, @RequestBody UserRequestDto userResponseDto) {
    return userService.update(id, userResponseDto);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> delete(@PathVariable("userId") UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
