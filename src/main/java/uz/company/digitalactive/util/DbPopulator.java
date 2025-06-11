package uz.company.digitalactive.util;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.company.digitalactive.dto.request.role.RoleRequestDto;
import uz.company.digitalactive.dto.request.user.UserRequestDto;
import uz.company.digitalactive.dto.response.role.RoleResponseDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.entity.Role;
import uz.company.digitalactive.service.role.RoleService;
import uz.company.digitalactive.service.user.UserService;

@Component
@RequiredArgsConstructor
public class DbPopulator implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        //                Role admin = getOrCreateAdmin();
        //
        //                List<UserResponseDto> usersByRole = userService.getAllByRole(admin);
        //                if (usersByRole.isEmpty()) {
        //                    userService.create(
        //                            new UserRequestDto(
        //                                    "Admin", "Adminov", "admin@gmail.com",
        //     "secret","+998905008450",
        //         List.of(admin.getId())
        //                            )
        //                    );
        //                }
    }

    private Role getOrCreateAdmin() {
        Optional<Role> optionalRole = roleService.getByName("ADMIN");
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        } else {
            RoleResponseDto admin = roleService.create(new RoleRequestDto("ADMIN", ""));

            Role role = new Role();
            role.setId(admin.id());
            role.setName(admin.name());
            role.setDescription(admin.description());

            return role;
        }
    }
}
