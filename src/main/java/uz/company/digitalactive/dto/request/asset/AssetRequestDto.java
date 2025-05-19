package uz.company.digitalactive.dto.request.asset;

import uz.company.digitalactive.entity.enums.AssetStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
// get view
public record AssetRequestDto(
    List<UUID> projectId,
    UUID typeId,
    LocalDate issuedDate,
    LocalDate expirationDate,
    String AssetName,
    String owner,
    String issuer,
    AssetStatus status) {}
