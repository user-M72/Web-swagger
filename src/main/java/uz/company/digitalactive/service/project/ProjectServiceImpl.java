package uz.company.digitalactive.service.project;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.company.digitalactive.dto.request.project.ProjectRequestDto;
import uz.company.digitalactive.dto.response.project.ProjectResponseDto;
import uz.company.digitalactive.entity.Project;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.mapper.ProjectMapper;
import uz.company.digitalactive.repository.ProjectRepository;
import uz.company.digitalactive.repository.UserRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
  @Autowired private ProjectRepository projectRepository;
  @Autowired private ProjectMapper projectMapper;
  @Autowired private UserRepository userRepository;

  @Override
  public List<ProjectResponseDto> get() {
    return projectRepository.findAll().stream()
        .map(projectMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public ProjectResponseDto getById(UUID id) {
    Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    return projectMapper.toDto(project);
  }

  @Override
  public ProjectResponseDto create(ProjectRequestDto projectRequestDto) {
    Project project = projectMapper.toEntity(projectRequestDto);
    User manager = userRepository.findById(projectRequestDto.projectManagerId())
            .orElseThrow(()-> new RuntimeException("Manager not found"));
    project.setProjectManager(manager);
    return projectMapper.toDto(projectRepository.save(project));
  }

  @Override
  public ProjectResponseDto update(UUID id, ProjectRequestDto projectRequestDto) {
    Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    projectMapper.updateFromDto(projectRequestDto, project);
    User manager = userRepository.findById(projectRequestDto.projectManagerId())
            .orElseThrow(()-> new RuntimeException("Manager not found"));
    project.setProjectManager(manager);
    return projectMapper.toDto(projectRepository.save(project));
  }

  @Override
  public void delete(UUID id) {
    projectRepository.deleteById(id);
  }
}
