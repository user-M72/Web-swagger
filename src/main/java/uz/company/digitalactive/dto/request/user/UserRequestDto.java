package uz.company.digitalactive.dto.request.user;

import java.util.List;
import java.util.UUID;

public record UserRequestDto(
    String firstname,
    String lastname,
    String email,
    String password,
    String phoneNumber,
    List<UUID> roleIds) {}
