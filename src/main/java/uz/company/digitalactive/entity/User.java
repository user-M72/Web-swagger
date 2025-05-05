package uz.company.digitalactive.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.company.digitalactive.entity.base.BaseDomain;

@Entity(name = "users")
@Getter
@Setter
public class User extends BaseDomain<UUID> implements UserDetails {

  private String firstname;
  private String lastname;

  @Column(unique = true)
  private String email;

  private String password;

  private Boolean enabled = true;

  @ManyToMany
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
  private Set<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return enabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return enabled;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public String getUsername() {
    return this.email;
  }
  ;
}
