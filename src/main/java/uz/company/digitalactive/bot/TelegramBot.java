package uz.company.digitalactive.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

  @Autowired private MessageService messageService;

  private final String username;

  public TelegramBot(String username, String token) {
    super(token);
    this.username = username;

    List<BotCommand> commandList = new ArrayList<>();
    commandList.add(new BotCommand("/start", "launch the bot"));
    commandList.add(new BotCommand("/info", "get information message"));
    commandList.add(new BotCommand("/help", "get help message"));
    try {
      this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
    } catch (TelegramApiException e) {throw new RuntimeException(e);
    }
  }

  @Override
  public String getBotUsername() {
    return username;
  }

  // все фуну-ии для обработки message

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      String chatId = message.getChatId().toString();

      SendMessage responseMessage = new SendMessage(chatId, "");

      if (message.hasText()) {
        String text = message.getText();
        messageService.handleMessage(text, responseMessage);
      }

      if (message.hasContact()) {
        Contact contact = message.getContact();
        messageService.handleContact(chatId, contact, responseMessage);
      }

      executeMessage(responseMessage);
    }
  }

  public void executeMessage(SendMessage sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
