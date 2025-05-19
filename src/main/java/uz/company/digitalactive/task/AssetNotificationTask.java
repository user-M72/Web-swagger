package uz.company.digitalactive.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.company.digitalactive.bot.Message;
import uz.company.digitalactive.bot.MessageRepository;
import uz.company.digitalactive.bot.MessageService;
import uz.company.digitalactive.bot.TelegramBot;
import uz.company.digitalactive.entity.DigitalAsset;
import uz.company.digitalactive.repository.DigitalAssetRepository;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class AssetNotificationTask {

    private final MessageService messageService;

    private final TelegramBot telegramBot;

    private final DigitalAssetRepository assetRepository;
    private final MessageRepository messageRepository;

    @Scheduled(cron = "0 57 15 * * *")
    public void notifyDaysLeft() {
        LocalDateTime now = LocalDateTime.now();

        List<DigitalAsset> assets = assetRepository.findByExpirationDateBetween(now, now.plusDays(3));

        List<Message> messages = messageRepository.findAll();
        for (Message message : messages) {

            for (DigitalAsset asset : assets) {

                String text = String.format(
                        "Reminder: Asset \"%s\" expires in 3 days (%s)",
                        asset.getName(),
                        asset.getExpirationDate().toLocalDate()
                );

                SendMessage responseMessage = SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(text)
                        .build();

                telegramBot.executeMessage(responseMessage);
            }
        }
    }
}

