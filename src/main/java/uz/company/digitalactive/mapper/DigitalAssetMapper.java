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

  @Mapping(target = "typeName", source = "type.name")
  @Mapping(target = "ownerName", source = "owner")
  @Mapping(target = "status", source = "status")
  AssetResponseDto toDto(DigitalAsset digitalAsset);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", defaultValue = "PENDING")
  DigitalAsset toEntity(AssetRequestDto assetRequestDto);

  void updateFromDto(AssetRequestDto assetRequestDto, @MappingTarget DigitalAsset digitalAsset);
}
