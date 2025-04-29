package uz.company.digitalactive.dto.request.asset;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AssetRequestDto(
    List<UUID> projectIds,
    LocalDate expirationDate,
    String typeName,
    String ownerName,
    String projectShortName) {}
