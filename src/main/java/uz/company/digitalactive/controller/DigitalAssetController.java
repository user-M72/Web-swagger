package uz.company.digitalactive.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.digitalactive.dto.AssetPatchDto;
import uz.company.digitalactive.dto.request.asset.AssetRequestDto;
import uz.company.digitalactive.dto.response.asset.AssetResponseDto;
import uz.company.digitalactive.service.digitalAsset.DigitalAssetService;

@RestController
@RequestMapping("/api/asset/v1")
public class DigitalAssetController {

  @Autowired private DigitalAssetService digitalAssetService;

  @GetMapping
  public List<AssetResponseDto> get() {
    return digitalAssetService.get();
  }

  @GetMapping("/{assetId}")
  public AssetResponseDto getById(@PathVariable("assetId") UUID id) {
    return digitalAssetService.getById(id);
  }

  @PostMapping
  public ResponseEntity<AssetResponseDto> create(@RequestBody AssetRequestDto assetRequestDto) {
    AssetResponseDto created = digitalAssetService.create(assetRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{assetId}")
  public AssetResponseDto update(
      @PathVariable("assetId") UUID id, @RequestBody AssetRequestDto assetRequestDto) {
    return digitalAssetService.update(id, assetRequestDto);
  }

  @PatchMapping("/{assetId}")
  public ResponseEntity<AssetResponseDto> patch(
      @PathVariable("assetId") UUID id, @RequestBody AssetPatchDto assetPatchDto) {
    AssetResponseDto assetResponseDto = digitalAssetService.patch(id, assetPatchDto);
    return ResponseEntity.ok(assetResponseDto);
  }

  @DeleteMapping("/{assetId}")
  public ResponseEntity<Void> delete(@PathVariable("assetId") UUID id) {
    digitalAssetService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
