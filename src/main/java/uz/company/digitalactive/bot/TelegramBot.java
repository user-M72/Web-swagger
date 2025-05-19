package uz.company.digitalactive.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private MessageService messageService;

    private final String username;

    public TelegramBot(String username, String token) {
        super(token);
        this.username = username;
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

            SendMessage responseMessage  = new SendMessage(chatId, "");

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

    public void executeMessage (SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
