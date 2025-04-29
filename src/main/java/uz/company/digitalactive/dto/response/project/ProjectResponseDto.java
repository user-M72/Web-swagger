package uz.company.digitalactive.dto.response.project;

import java.util.UUID;

public record ProjectResponseDto(UUID id, String shortName, String name, String description) {}
