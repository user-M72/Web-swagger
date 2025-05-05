package uz.company.digitalactive.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

  @Autowired
  private  MessageService messageService;

  private final String username;

    public TelegramBot(String username, String token) {
    super(token);
    this.username = username;
    }

  @Override
  public String getBotUsername() {
    return username;
  }

    // все фуну-ии для обработки message // создать интерфейс или switch!!!!

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {

      String chatId = update.getMessage().getChatId().toString();
      String userMessage = update.getMessage().getText();

      String response = messageService.handleMessage(chatId, userMessage);

      SendMessage message = SendMessage.builder()
              .chatId(chatId)
              .text(response)
              .build();

      try {
        execute(message);
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
