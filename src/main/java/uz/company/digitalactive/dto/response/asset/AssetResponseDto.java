package uz.company.digitalactive.dto.response.asset;

import java.time.LocalDate;
import java.util.UUID;

public record AssetResponseDto(
    UUID id,
    LocalDate expirationDate,
    String typeName,
    String ownerName,
    String projectShortName) {}
