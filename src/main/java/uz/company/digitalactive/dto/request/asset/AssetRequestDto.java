package uz.company.digitalactive.dto.request.asset;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import uz.company.digitalactive.entity.enums.AssetStatus;

// get view
public record AssetRequestDto(
    List<UUID> projectId,
    UUID typeId,
    LocalDate issuedDate,
    LocalDate expirationDate,
    String name,
    String owner,
    String issuer,
    AssetStatus status) {}
