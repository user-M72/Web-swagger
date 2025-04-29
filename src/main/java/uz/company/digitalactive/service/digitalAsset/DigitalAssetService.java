package uz.company.digitalactive.service.digitalAsset;

import java.util.List;
import java.util.UUID;
import uz.company.digitalactive.dto.AssetPatchDto;
import uz.company.digitalactive.dto.request.asset.AssetRequestDto;
import uz.company.digitalactive.dto.response.asset.AssetResponseDto;

public interface DigitalAssetService {
  List<AssetResponseDto> get();

  AssetResponseDto getById(UUID id);

  AssetResponseDto create(AssetRequestDto assetRequestDto);

  AssetResponseDto update(UUID id, AssetRequestDto assetRequestDto);

  AssetResponseDto patch(UUID id, AssetPatchDto assetPatchDto);

  void delete(UUID id);
}
