package uz.company.digitalactive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.company.digitalactive.dto.AuthResponseDto;
import uz.company.digitalactive.dto.RegisterRequestDto;
import uz.company.digitalactive.entity.User;

@Mapper(componentModel = "spring")
public interface AuthMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "enabled", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "phoneNumber", ignore = true)
  User toDto(RegisterRequestDto registerRequestDto);

  @Mapping(target = "token", ignore = true)
  @Mapping(target = "role", ignore = true)
  AuthResponseDto toEntity(User user);
}
