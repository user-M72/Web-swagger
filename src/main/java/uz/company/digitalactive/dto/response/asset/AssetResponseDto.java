package uz.company.digitalactive.dto.response.asset;

import java.time.LocalDate;
import java.util.UUID;
import uz.company.digitalactive.entity.enums.AssetStatus;

public record AssetResponseDto(
    UUID id,
    String name,
    LocalDate issuedDate,
    LocalDate expirationDate,
    String typeName,
    String owner,
    String projectShortName,
    String issuer,
    AssetStatus status) {}
