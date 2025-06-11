package uz.company.digitalactive.service.user;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.company.digitalactive.dto.request.user.UserRequestDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.entity.Role;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.entity.enums.UserSort;
import uz.company.digitalactive.exception.NotFoundException;
import uz.company.digitalactive.mapper.UserMapper;
import uz.company.digitalactive.repository.UserRepository;
import uz.company.digitalactive.service.role.RoleService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> get() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getById(UUID id) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        Set<Role> roleList = roleService.getByIdList(dto.roleIds());
        User user = userMapper.toEntity(dto, roleList, passwordEncoder.encode(dto.password()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto update(UUID id, UserRequestDto userRequestDto) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Set<Role> roles = roleService.getByIdList(userRequestDto.roleIds());
        userMapper.updateFromDto(userRequestDto, roles, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> getAllByRole(Role admin) {
        return List.of();
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found by phone number: " + phoneNumber));
    }

    @Override
    public Page<UserResponseDto> getPaginated(
            int page, int size, UserSort sortBy, String direction, String search) {
        List<String> allowedSortField = List.of("firstname", "lastname", "email", "phoneNumber");

        String sortField = sortBy.getField();
        if (!allowedSortField.contains(sortField)) {
            throw new RuntimeException("Not found: " + sortBy);
        }

        Sort sort =
                direction.equalsIgnoreCase("asc")
                        ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage = userRepository.findUserPage(search, pageable);

        return userPage.map(userMapper::toDto);
    }

    public Page<User> getUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
}
