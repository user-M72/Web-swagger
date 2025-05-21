package uz.company.digitalactive.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {
  //  @Value("${telegrambots.botToken}")
  //  private String botToken;

  //  @Value("${telegrambots.botUsername}")
  //  private String botUsername;

  //  @Bean
  //  public BotSession botSession(TelegramBot bot, TelegramBotsApi botsApi)
  //      throws TelegramApiException {
  //    return botsApi.registerBot(bot);
  //  }

  @Bean
  public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
    botsApi.registerBot(telegramBot);
    return new TelegramBotsApi(DefaultBotSession.class);
  }

  @Bean
  public TelegramBot telegramBot(
      @Value("${telegrambots.botToken}") String botToken,
      @Value("${telegrambots.botUsername}") String botUsername) {
    return new TelegramBot(botUsername, botToken);
  }
}
