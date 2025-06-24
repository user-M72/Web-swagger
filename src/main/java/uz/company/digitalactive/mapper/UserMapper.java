package uz.company.digitalactive.mapper;

import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uz.company.digitalactive.dto.request.user.UserRequestDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.entity.Role;
import uz.company.digitalactive.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserResponseDto toDto(User user);

  @Mapping(target = "roles", source = "roles")
  @Mapping(target = "password", source = "encodedPassword")
  User toEntity(UserRequestDto userRequestDto, Set<Role> roles, String encodedPassword);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", source = "roles")
  void updateFromDto(UserRequestDto dto, Set<Role> roles, @MappingTarget User user);
}
