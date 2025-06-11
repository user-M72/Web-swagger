package uz.company.digitalactive.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.digitalactive.dto.request.role.RoleRequestDto;
import uz.company.digitalactive.dto.response.role.RoleResponseDto;
import uz.company.digitalactive.service.role.RoleService;

@RestController
@RequestMapping("/api/roles/v1")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleResponseDto> get() {
        return roleService.getAll();
    }

    @GetMapping("/{roleId}")
    public RoleResponseDto getById(@PathVariable("roleId") UUID id) {
        return roleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> create(@RequestBody RoleRequestDto roleRequestDto) {
        RoleResponseDto created = roleService.create(roleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{roleId}")
    public RoleResponseDto update(
            @PathVariable("roleId") UUID id, @RequestBody RoleRequestDto roleRequestDto) {
        return roleService.update(id, roleRequestDto);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> delete(@PathVariable("roleId") UUID id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
