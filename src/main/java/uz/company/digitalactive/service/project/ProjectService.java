package uz.company.digitalactive.service.project;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import uz.company.digitalactive.dto.request.project.ProjectRequestDto;
import uz.company.digitalactive.dto.response.project.ProjectResponseDto;
import uz.company.digitalactive.entity.enums.ProjectSort;

public interface ProjectService {
    List<ProjectResponseDto> get();

    ProjectResponseDto getById(UUID id);

    ProjectResponseDto create(ProjectRequestDto projectRequestDto);

    ProjectResponseDto update(UUID id, ProjectRequestDto projectRequestDto);

    void delete(UUID id);

    Page<ProjectResponseDto> getPaginated(
            int page, int size, ProjectSort sortBy, String direction, String search);
}
