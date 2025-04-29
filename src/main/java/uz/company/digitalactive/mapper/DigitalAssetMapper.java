package uz.company.digitalactive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uz.company.digitalactive.dto.request.asset.AssetRequestDto;
import uz.company.digitalactive.dto.response.asset.AssetResponseDto;
import uz.company.digitalactive.entity.DigitalAsset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DigitalAssetMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "typeName", ignore = true)
  @Mapping(target = "ownerName", ignore = true)
  @Mapping(target = "projectShortName", ignore = true)
  AssetResponseDto toDto(DigitalAsset digitalAsset);

  @Mapping(target = "name", ignore = true)
  @Mapping(target = "type", ignore = true)
  @Mapping(target = "description", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "issuer", ignore = true)
  @Mapping(target = "issuedDate", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "projects", ignore = true)
  @Mapping(target = "id", ignore = true)
  DigitalAsset toEntity(AssetRequestDto assetRequestDto);


  void updateFromDto(AssetRequestDto assetRequestDto, @MappingTarget DigitalAsset digitalAsset);
}
