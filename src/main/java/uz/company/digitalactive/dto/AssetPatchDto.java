package uz.company.digitalactive.dto;

import java.time.LocalDateTime;
import uz.company.digitalactive.entity.enums.AssetStatus;

public record AssetPatchDto(
    String name,
    String description,
    String owner,
    String issuer,
    LocalDateTime issuedDate,
    LocalDateTime expirationDate,
    AssetStatus status) {}
