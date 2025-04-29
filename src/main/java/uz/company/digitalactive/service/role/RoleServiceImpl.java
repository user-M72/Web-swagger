package uz.company.digitalactive.service.role;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.company.digitalactive.dto.request.role.RoleRequestDto;
import uz.company.digitalactive.dto.response.role.RoleResponseDto;
import uz.company.digitalactive.entity.Role;
import uz.company.digitalactive.mapper.RoleMapper;
import uz.company.digitalactive.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired private RoleRepository roleRepository;
  @Autowired private RoleMapper roleMapper;

  @Override
  public List<RoleResponseDto> getAll() {
    return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public RoleResponseDto getById(UUID id) {
    Role role =
        roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    return roleMapper.toDto(role);
  }

  @Override
  public RoleResponseDto create(RoleRequestDto roleRequestDto) {
    Role role = roleMapper.toEntity(roleRequestDto);
    return roleMapper.toDto(roleRepository.save(role));
  }

  @Override
  public RoleResponseDto update(UUID id, RoleRequestDto roleRequestDto) {
    Role role =
        roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    roleMapper.updateFromDto(roleRequestDto, role);
    return roleMapper.toDto(roleRepository.save(role));
  }

  @Override
  public void delete(UUID id) {
    if (!roleRepository.existsById(id)) {
      throw new RuntimeException("Role not found");
    }
    roleRepository.deleteById(id);
  }

  @Override
  public Set<Role> getByIdList(List<UUID> uuids) {
    return new HashSet<>(roleRepository.findAllById(uuids));
  }

  @Override
  public Optional<Role> getByName(String admin) {
    return Optional.empty();
  }
}
