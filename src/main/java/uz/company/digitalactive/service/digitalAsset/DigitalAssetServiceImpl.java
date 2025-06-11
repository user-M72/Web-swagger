package uz.company.digitalactive.service.digitalAsset;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.company.digitalactive.dto.AssetPatchDto;
import uz.company.digitalactive.dto.request.asset.AssetRequestDto;
import uz.company.digitalactive.dto.response.asset.AssetResponseDto;
import uz.company.digitalactive.entity.DigitalAsset;
import uz.company.digitalactive.entity.Project;
import uz.company.digitalactive.entity.Type;
import uz.company.digitalactive.entity.enums.AssetSort;
import uz.company.digitalactive.entity.enums.AssetStatus;
import uz.company.digitalactive.mapper.DigitalAssetMapper;
import uz.company.digitalactive.repository.DigitalAssetRepository;
import uz.company.digitalactive.repository.ProjectRepository;
import uz.company.digitalactive.repository.TypeRepository;

@Service
public class DigitalAssetServiceImpl implements DigitalAssetService {

    private final DigitalAssetRepository digitalAssetRepository;
    private final DigitalAssetMapper digitalAssetMapper;
    private final TypeRepository typeRepository;
    private final ProjectRepository projectRepository;

    public DigitalAssetServiceImpl(
            DigitalAssetRepository digitalAssetRepository,
            DigitalAssetMapper digitalAssetMapper,
            TypeRepository typeRepository,
            ProjectRepository projectRepository) {
        this.digitalAssetRepository = digitalAssetRepository;
        this.digitalAssetMapper = digitalAssetMapper;
        this.typeRepository = typeRepository;
        this.projectRepository = projectRepository;
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

        Type type =
                typeRepository
                        .findById(assetRequestDto.typeId())
                        .orElseThrow(() -> new RuntimeException("Type not found"));
        digitalAsset.setType(type);

        List<UUID> projectIds = assetRequestDto.projectId();
        List<Project> projects = projectRepository.findAllById(projectIds);
        if (projects.size() != projectIds.size()) {
            throw new RuntimeException("Some project not found");
        }
        digitalAsset.setProjects(projects);

        return digitalAssetMapper.toDto(digitalAssetRepository.save(digitalAsset));
    }

    @Override
    public AssetResponseDto update(UUID id, AssetRequestDto assetRequestDto) {
        DigitalAsset digitalAsset =
                digitalAssetRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        digitalAssetMapper.updateFromDto(assetRequestDto, digitalAsset);

        Type type =
                typeRepository
                        .findById(assetRequestDto.typeId())
                        .orElseThrow(() -> new RuntimeException("Type not found"));
        digitalAsset.setType(type);

        List<UUID> projectIds = assetRequestDto.projectId();
        List<Project> projects = projectRepository.findAllById(projectIds);
        if (projects.size() != projectIds.size()) {
            throw new RuntimeException("Some project not found");
        }
        digitalAsset.setProjects(projects);

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

    @Override
    public Page<AssetResponseDto> getAllPaginated(
            int page, int size, AssetSort sortBy, String direction, String search, AssetStatus status) {

        List<String> allowedSortField =
                List.of("name", "description", "owner", "issuer", "issuedDate", "expirationDate", "status");

        String sortField = sortBy.getField();
        if (!allowedSortField.contains(sortField)) {
            throw new RuntimeException("Not found: " + sortBy);
        }

        Sort sort =
                direction.equalsIgnoreCase("asc")
                        ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<DigitalAsset> assetPage = digitalAssetRepository.findAssetsPage(search, status, pageable);

        return assetPage.map(digitalAssetMapper::toDto);
    }

    public Page<DigitalAsset> getAsset(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return digitalAssetRepository.findAll(pageable);
    }
}
