package uz.company.digitalactive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.company.digitalactive.dto.request.role.RoleRequestDto;
import uz.company.digitalactive.dto.response.role.RoleResponseDto;
import uz.company.digitalactive.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDto toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", expression = "java(roleRequestDto.getName().toUpperCase())")
    Role toEntity(RoleRequestDto roleRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(RoleRequestDto roleRequestDto, @MappingTarget Role role);
}
