package uz.company.digitalactive.service.type;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.company.digitalactive.dto.request.type.TypeRequestDto;
import uz.company.digitalactive.dto.response.type.TypeResponseDto;
import uz.company.digitalactive.entity.Type;
import uz.company.digitalactive.mapper.TypeMapper;
import uz.company.digitalactive.repository.TypeRepository;

@Service
public class TypeServiceImpl implements TypeService {
  @Autowired private TypeRepository typeRepository;
  @Autowired private TypeMapper typeMapper;

  @Override
  public List<TypeResponseDto> get() {
    return typeRepository.findAll().stream().map(typeMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public TypeResponseDto getById(UUID id) {
    Type type =
        typeRepository.findById(id).orElseThrow(() -> new RuntimeException("AssetType not found"));
    return typeMapper.toDto(type);
  }

  @Override
  public TypeResponseDto create(TypeRequestDto typeRequestDto) {
    Type type = typeMapper.toEntity(typeRequestDto);
    return typeMapper.toDto(typeRepository.save(type));
  }

  @Override
  public TypeResponseDto update(UUID id, TypeRequestDto typeRequestDto) {
    Type type = typeRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    typeMapper.updateFromDto(typeRequestDto, type);
    return typeMapper.toDto(typeRepository.save(type));
  }

  @Override
  public void delete(UUID id) {
    typeRepository.deleteById(id);
  }
}
