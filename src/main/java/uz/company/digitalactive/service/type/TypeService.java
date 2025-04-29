package uz.company.digitalactive.service.type;

import java.util.List;
import java.util.UUID;
import uz.company.digitalactive.dto.request.type.TypeRequestDto;
import uz.company.digitalactive.dto.response.type.TypeResponseDto;

public interface TypeService {
  List<TypeResponseDto> get();

  TypeResponseDto getById(UUID id);

  TypeResponseDto create(TypeRequestDto typeRequestDto);

  TypeResponseDto update(UUID id, TypeRequestDto typeRequestDto);

  void delete(UUID id);
}
