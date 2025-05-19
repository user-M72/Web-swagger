package uz.company.digitalactive.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import uz.company.digitalactive.entity.base.BaseDomain;
import uz.company.digitalactive.entity.enums.AssetStatus;

@Entity
@Getter
@Setter
public class DigitalAsset extends BaseDomain<UUID> {

  private String name;

  @ManyToOne
  @JoinColumn(name = "type_id")
  private Type type;

  private String description;

  private String owner;

  private String issuer;

  private LocalDateTime issuedDate;

  private LocalDateTime expirationDate;

  @Enumerated(EnumType.STRING)
  private AssetStatus status;

  @ManyToMany
  @JoinTable(
      name = "assets_projects",
      joinColumns = @JoinColumn(name = "asset_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "project_id", nullable = false))
  private List<Project> projects;

  //    private JSONB metaData;

}
