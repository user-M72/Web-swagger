package uz.company.digitalactive.dto.request.project;

import java.util.UUID;

public record ProjectRequestDto(
    String shortName, String name, String description, UUID projectManagerId) {}
