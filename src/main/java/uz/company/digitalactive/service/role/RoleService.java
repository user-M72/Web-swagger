package uz.company.digitalactive.service.role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import uz.company.digitalactive.dto.request.role.RoleRequestDto;
import uz.company.digitalactive.dto.response.role.RoleResponseDto;
import uz.company.digitalactive.entity.Role;

public interface RoleService {
  List<RoleResponseDto> getAll();

  RoleResponseDto getById(UUID id);

  RoleResponseDto create(RoleRequestDto roleRequestDto);

  RoleResponseDto update(UUID id, RoleRequestDto roleRequestDto);

  void delete(UUID id);

  Set<Role> getByIdList(List<UUID> uuids);

  Optional<Role> getByName(String name);
}
