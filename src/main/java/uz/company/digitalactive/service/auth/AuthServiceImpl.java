package uz.company.digitalactive.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.company.digitalactive.dto.AuthResponseDto;
import uz.company.digitalactive.dto.LoginDto;
import uz.company.digitalactive.dto.request.user.PasswordRequestDto;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.mapper.AuthMapper;
import uz.company.digitalactive.repository.RoleRepository;
import uz.company.digitalactive.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AuthResponseDto login(LoginDto authRequestDto) {
        User user =
                userRepository
                        .findByEmail(authRequestDto.email())
                        .orElseThrow(() -> new RuntimeException("email not found"));

        if (!authRequestDto.password().equals(user.getPassword())) {
            throw new RuntimeException("INVALID email or password");
        }
        ;
        return authMapper.toEntity(user);
    }

    @Override
    public Boolean changePassword(User currentUser, PasswordRequestDto passwordDto) {
        if (!passwordDto.oldPassword().equals(currentUser.getPassword())) {
            throw new RuntimeException("Incorrect old password");
        }
        currentUser.setPassword(passwordDto.newPassword());
        userRepository.save(currentUser);
        return true;
    }
}
