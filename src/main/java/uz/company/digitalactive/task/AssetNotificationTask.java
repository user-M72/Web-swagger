package uz.company.digitalactive.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.company.digitalactive.bot.Message;
import uz.company.digitalactive.bot.MessageRepository;
import uz.company.digitalactive.bot.MessageService;
import uz.company.digitalactive.bot.TelegramBot;
import uz.company.digitalactive.entity.DigitalAsset;
import uz.company.digitalactive.entity.Project;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.repository.DigitalAssetRepository;

@Component
@RequiredArgsConstructor
public class AssetNotificationTask {

    private final MessageService messageService;

    private final TelegramBot telegramBot;

    private final DigitalAssetRepository assetRepository;
    private final MessageRepository messageRepository;

    @Scheduled(cron = "0 21 15 * * *")
    @Transactional(readOnly = true)
    public void notifyDaysLeft() {
        LocalDateTime now = LocalDateTime.now();

        List<DigitalAsset> assets = assetRepository.findByExpirationDateBetween(now, now.plusDays(3));

        for (DigitalAsset asset : assets) {

            for (Project project : asset.getProjects()) {
                User projectManager = project.getProjectManager();
                Optional<Message> optionalMessage = messageRepository.findByUser(projectManager);
                if (optionalMessage.isPresent()) {
                    Message message = optionalMessage.get();
                    sendTgNotificationByChatId(message.getChatId(), asset);
                }
            }
        }
    }

    private void sendTgNotificationByChatId(String chatId, DigitalAsset asset) {
        String text =
                String.format(
                        "Reminder: Asset \"%s\" expires in 3 days (%s)",
                        asset.getName(), asset.getExpirationDate().toLocalDate());

        SendMessage responseMessage =
                SendMessage.builder().chatId(chatId).text(text).build();

        telegramBot.executeMessage(responseMessage);
    }
}
