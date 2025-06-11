package uz.company.digitalactive.entity;

import jakarta.persistence.Entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import uz.company.digitalactive.entity.base.BaseDomain;

@Entity()
@Getter
@Setter
public class Type extends BaseDomain<UUID> {

    private String name;
}
