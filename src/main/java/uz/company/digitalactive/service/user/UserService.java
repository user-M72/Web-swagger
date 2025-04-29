package uz.company.digitalactive.service.user;

import java.util.List;
import java.util.UUID;
import uz.company.digitalactive.dto.request.user.UserRequestDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.entity.Role;

public interface UserService {
  List<UserResponseDto> get();

  UserResponseDto getById(UUID id);

  UserResponseDto create(UserRequestDto userRequestDto);

  UserResponseDto update(UUID id, UserRequestDto userRequestDto);

  void delete(UUID id);

  List<UserResponseDto> getAllByRole(Role admin);
}
