package uz.company.digitalactive.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.company.digitalactive.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {}
