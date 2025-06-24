package uz.company.digitalactive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.company.digitalactive.dto.request.type.TypeRequestDto;
import uz.company.digitalactive.dto.response.type.TypeResponseDto;
import uz.company.digitalactive.entity.Type;

@Mapper(componentModel = "spring")
public interface TypeMapper {

  TypeResponseDto toDto(Type type);

  @Mapping(target = "id", ignore = true)
  Type toEntity(TypeRequestDto typeRequestDto);

  @Mapping(target = "id", ignore = true)
  void updateFromDto(TypeRequestDto typeRequestDto, @MappingTarget Type type);
}
