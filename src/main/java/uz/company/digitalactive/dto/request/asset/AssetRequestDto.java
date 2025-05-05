package uz.company.digitalactive.dto.request.asset;

import uz.company.digitalactive.entity.enums.AssetStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AssetRequestDto(
    List<UUID> projectId,
    LocalDate issuedDate,
    LocalDate expirationDate,
    String typeName,
    String ownerName,
    String projectShortName,
    String issuer,
    AssetStatus status) {}
