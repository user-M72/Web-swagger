package uz.company.digitalactive.bot;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.entity.base.BaseDomain;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "bot")
@Getter
@Setter
public class Message extends BaseDomain<UUID> {

    @OneToOne
    private User user;

    private String chatId;
    private String phoneNumber;
    //private LocalDateTime timestamp;
}
