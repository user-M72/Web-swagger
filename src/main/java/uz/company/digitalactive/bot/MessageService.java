package uz.company.digitalactive.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private UserMessageRepository userMessageRepository;

    public String handleMessage(String chatId, String userMessage) {
        if (!userMessage.startsWith("/")) {
            UserMessage message = new UserMessage();
            message.setChatId(chatId);
            message.setText(userMessage);
            message.setTimestamp(LocalDateTime.now());
            userMessageRepository.save(message);
        }

        if(userMessage.equalsIgnoreCase("/start")){
            return "Hello! I'm your Telegram bot!";
        } else if (userMessage.equalsIgnoreCase("/history")) {
            List<UserMessage> messages = userMessageRepository.findByChatId(chatId);
            return messages.stream()
                    .map(message -> message.getText() + "->" + message.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .collect(Collectors.joining("\n"));

        }
        return null;
    }
}
