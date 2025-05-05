package uz.company.digitalactive.dto.response.asset;

import uz.company.digitalactive.entity.enums.AssetStatus;

import java.time.LocalDate;
import java.util.UUID;

public record AssetResponseDto(
    UUID id,
    LocalDate issuedDate,
    LocalDate expirationDate,
    String typeName,
    String ownerName,
    String projectShortName,
    String issuer,
    AssetStatus status
    ) {}
