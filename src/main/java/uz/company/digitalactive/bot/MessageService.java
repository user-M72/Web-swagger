package uz.company.digitalactive.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.company.digitalactive.entity.DigitalAsset;
import uz.company.digitalactive.entity.User;
import uz.company.digitalactive.repository.DigitalAssetRepository;
import uz.company.digitalactive.service.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    public void handleMessage(String text, SendMessage message) {
        if (text.equalsIgnoreCase("/start")) {
            message.setText("Hello! User, I'm your Telegram bot! Please, click share contact button!");
            message.setReplyMarkup(createKeyboardRequestPhone());
        } else if (text.equalsIgnoreCase("/help")) {
            message.setText("Bro I'm really sorry, but I can't help you!");
        } else {
            message.setText("Please, type /help");
        }
    }

    public void handleContact(String chatId, Contact contact, SendMessage message) {
        String phoneNumber = contact.getPhoneNumber();
        String plus = normalizePhoneNumber(phoneNumber);
        create(chatId, plus);

        message.setText("You have been added! Thank you for sharing your phone number.");
        message.setReplyMarkup(new ReplyKeyboardRemove(true));
    }

    public void create(String chatId, String phoneNumber) {
        User user = userService.findByPhoneNumber(phoneNumber);
        Message message = new Message();
        message.setUser(user);
        message.setChatId(chatId);
        message.setPhoneNumber(phoneNumber);
        try {
            messageRepository.save(message);
        } catch (DataIntegrityViolationException ignored) {

        }
    }

    private ReplyKeyboardMarkup createKeyboardRequestPhone() {
        KeyboardButton button = new KeyboardButton();
        button.setText("Send phone number");
        button.setRequestContact(true);

        KeyboardRow row = new KeyboardRow();
        row.add(button);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(row));

        return keyboardMarkup;
    }

    private String normalizePhoneNumber(String phoneNumber) {
        if (!phoneNumber.startsWith("+")) {
            return "+" + phoneNumber;
        }
        return phoneNumber;
    }
}
