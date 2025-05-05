package uz.company.digitalactive.bot;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import uz.company.digitalactive.entity.base.BaseDomain;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "bot")
@Getter
@Setter
public class UserMessage extends BaseDomain<UUID> {

    private String chatId;
    private String text;
    private LocalDateTime timestamp;
}
