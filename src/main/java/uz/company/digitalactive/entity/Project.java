package uz.company.digitalactive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import uz.company.digitalactive.entity.base.BaseDomain;

@Entity
@Getter
@Setter
public class Project extends BaseDomain<UUID> {

  private String shortName;
  private String name;
  private String description;

  @ManyToOne private User projectManager;
}
