package uz.company.digitalactive.bot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.entity.base.BaseDomain;

@Entity(name = "bot")
@Getter
@Setter
public class Message extends BaseDomain<UUID> {

  @OneToOne private User user;

  private String chatId;

  @Column(nullable = false, unique = true)
  private String phoneNumber;
  // private LocalDateTime timestamp;
}
