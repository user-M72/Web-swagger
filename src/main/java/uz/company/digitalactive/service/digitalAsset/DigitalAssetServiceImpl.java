package uz.company.digitalactive.service.digitalAsset;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import uz.company.digitalactive.dto.AssetPatchDto;
import uz.company.digitalactive.dto.request.asset.AssetRequestDto;
import uz.company.digitalactive.dto.response.asset.AssetResponseDto;
import uz.company.digitalactive.entity.DigitalAsset;
import uz.company.digitalactive.mapper.DigitalAssetMapper;
import uz.company.digitalactive.repository.DigitalAssetRepository;

@Service
public class DigitalAssetServiceImpl implements DigitalAssetService {

  private final DigitalAssetRepository digitalAssetRepository;
  private final DigitalAssetMapper digitalAssetMapper;

  public DigitalAssetServiceImpl(
      DigitalAssetRepository digitalAssetRepository, DigitalAssetMapper digitalAssetMapper) {
    this.digitalAssetRepository = digitalAssetRepository;
    this.digitalAssetMapper = digitalAssetMapper;
  }

  @Override
  public List<AssetResponseDto> get() {
    return digitalAssetRepository.findAll().stream()
        .map(digitalAssetMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public AssetResponseDto getById(UUID id) {
    DigitalAsset digitalAsset =
        digitalAssetRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    return digitalAssetMapper.toDto(digitalAsset);
  }

  @Override
  public AssetResponseDto create(AssetRequestDto assetRequestDto) {
    DigitalAsset digitalAsset = digitalAssetMapper.toEntity(assetRequestDto);
    return digitalAssetMapper.toDto(digitalAssetRepository.save(digitalAsset));
  }

  @Override
  public AssetResponseDto update(UUID id, AssetRequestDto assetRequestDto) {
    DigitalAsset digitalAsset =
        digitalAssetRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    digitalAssetMapper.updateFromDto(assetRequestDto, digitalAsset);
    return digitalAssetMapper.toDto(digitalAssetRepository.save(digitalAsset));
  }

  @Override
  public AssetResponseDto patch(UUID id, AssetPatchDto assetPatchDto) {
    DigitalAsset digitalAsset =
        digitalAssetRepository.findById(id).orElseThrow(() -> new RuntimeException(""));

    if (assetPatchDto.name() != null) digitalAsset.setName(assetPatchDto.name());
    if (assetPatchDto.description() != null)
      digitalAsset.setDescription(assetPatchDto.description());
    if (assetPatchDto.owner() != null) digitalAsset.setOwner(assetPatchDto.owner());
    if (assetPatchDto.issuer() != null) digitalAsset.setIssuer(assetPatchDto.issuer());
    if (assetPatchDto.issuedDate() != null) digitalAsset.setIssuedDate(assetPatchDto.issuedDate());
    if (assetPatchDto.expirationDate() != null)
      digitalAsset.setExpirationDate(assetPatchDto.expirationDate());
    if (assetPatchDto.status() != null) digitalAsset.setStatus(assetPatchDto.status());

    DigitalAsset updated = digitalAssetRepository.save(digitalAsset);
    return digitalAssetMapper.toDto(updated);
  }

  @Override
  public void delete(UUID id) {
    digitalAssetRepository.deleteById(id);
  }
}
