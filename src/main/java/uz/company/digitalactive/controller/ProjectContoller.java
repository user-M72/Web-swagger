package uz.company.digitalactive.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.digitalactive.dto.request.project.ProjectRequestDto;
import uz.company.digitalactive.dto.response.project.ProjectResponseDto;
import uz.company.digitalactive.entity.enums.ProjectSort;
import uz.company.digitalactive.service.project.ProjectService;

@RestController
@RequestMapping("/api/project/v1")
public class ProjectContoller {

  @Autowired private ProjectService projectService;

  @GetMapping
  public List<ProjectResponseDto> get() {
    return projectService.get();
  }

  @GetMapping("/{projectId}")
  public ProjectResponseDto getById(@PathVariable("projectId") UUID id) {
    return projectService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ProjectResponseDto> create(
      @RequestBody ProjectRequestDto projectRequestDto) {
    ProjectResponseDto created = projectService.create(projectRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{projectId}")
  public ProjectResponseDto update(
      @PathVariable("projectId") UUID id, @RequestBody ProjectRequestDto projectRequestDto) {
    return projectService.update(id, projectRequestDto);
  }

  @DeleteMapping("/{projectId}")
  public ResponseEntity<Void> delete(@PathVariable("projectId") UUID id) {
    projectService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/paginated")
  public ResponseEntity<Page<ProjectResponseDto>> getPaginated(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam ProjectSort sortBy,
      @RequestParam(defaultValue = "asc") String direction,
      @RequestParam(required = false) String search) {
    Page<ProjectResponseDto> projects =
        projectService.getPaginated(page, size, sortBy, direction, search);
    return ResponseEntity.ok(projects);
  }
}
