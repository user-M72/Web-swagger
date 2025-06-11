package uz.company.digitalactive.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import uz.company.digitalactive.dto.request.user.UserRequestDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.entity.Role;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.entity.enums.UserSort;

public interface UserService {
    List<UserResponseDto> get();

    UserResponseDto getById(UUID id);

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto update(UUID id, UserRequestDto userRequestDto);

    void delete(UUID id);

    List<UserResponseDto> getAllByRole(Role admin);

    User findByPhoneNumber(String phoneNumber);

    Page<UserResponseDto> getPaginated(
            int page, int size, UserSort sortBy, String direction, String search);
}
