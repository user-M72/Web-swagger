package uz.company.digitalactive.dto.response.user;

import java.util.Set;
import java.util.UUID;
import uz.company.digitalactive.entity.Role;

public record UserResponseDto(
    UUID id,
    String firstname,
    String lastname,
    String email,
    String phoneNumber,
    Set<Role> roles) {}
