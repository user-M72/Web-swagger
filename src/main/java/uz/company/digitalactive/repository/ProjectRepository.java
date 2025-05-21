package uz.company.digitalactive.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.company.digitalactive.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
  @Query(
      """
            SELECT p
            FROM Project p
            WHERE
                (
                    :search IS NULL
                    OR LOWER(p.name) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                    OR LOWER(p.shortName) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                    OR LOWER(p.description) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                )
            """)
  Page<Project> findProjectPage(String search, Pageable pageable);
}
