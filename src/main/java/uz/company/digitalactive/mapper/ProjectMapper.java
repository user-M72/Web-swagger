package uz.company.digitalactive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uz.company.digitalactive.dto.request.project.ProjectRequestDto;
import uz.company.digitalactive.dto.response.project.ProjectResponseDto;
import uz.company.digitalactive.entity.Project;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
  ProjectResponseDto toDto(Project project);

  @Mapping(target = "id", ignore = true)
  Project toEntity(ProjectRequestDto projectRequestDto);

  @Mapping(target = "id", ignore = true)
  void updateFromDto(ProjectRequestDto projectRequestDto, @MappingTarget Project project);
}
