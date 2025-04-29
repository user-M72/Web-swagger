package uz.company.digitalactive.dto.auth;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TokenInfoResponse(
    UUID userId, String token, List<String> authorities, String email) {}
