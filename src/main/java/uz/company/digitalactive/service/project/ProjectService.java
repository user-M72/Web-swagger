package uz.company.digitalactive.service.project;

import java.util.List;
import java.util.UUID;
import uz.company.digitalactive.dto.request.project.ProjectRequestDto;
import uz.company.digitalactive.dto.response.project.ProjectResponseDto;

public interface ProjectService {
  List<ProjectResponseDto> get();

  ProjectResponseDto getById(UUID id);

  ProjectResponseDto create(ProjectRequestDto projectRequestDto);

  ProjectResponseDto update(UUID id, ProjectRequestDto projectRequestDto);

  void delete(UUID id);
}
