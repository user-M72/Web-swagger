package uz.company.digitalactive.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

  @Bean
  public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
    botsApi.registerBot(telegramBot);
    return new TelegramBotsApi(DefaultBotSession.class);
  }

  @Bean
  public TelegramBot telegramBot() {
    Dotenv dotenv = Dotenv.load();
    return new TelegramBot(dotenv.get("BOT_NAME"), dotenv.get("BOT_TOKEN"));
  }
}
