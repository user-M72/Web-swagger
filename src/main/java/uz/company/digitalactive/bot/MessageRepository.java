package uz.company.digitalactive.bot;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.company.digitalactive.entity.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findByChatId(String chatId);

  boolean existsByChatId(@NonNull String chatId);

  Optional<Message> findByUser(User user);
}
