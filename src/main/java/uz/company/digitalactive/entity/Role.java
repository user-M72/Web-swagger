package uz.company.digitalactive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import uz.company.digitalactive.entity.base.BaseDomain;

@Entity
@Getter
@Setter
public class Role extends BaseDomain<UUID> implements GrantedAuthority {

    @Column(unique = true)
    private String name;

    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
