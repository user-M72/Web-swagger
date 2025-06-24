package uz.company.digitalactive.dto.response;

import java.util.List;
import java.util.UUID;

public record LoginResponseDto(UUID userId, String token, List<String> authorities, String email) {}
