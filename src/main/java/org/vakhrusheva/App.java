package org.vakhrusheva;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {

  public static void main(String[] args) {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BotConfig.class);
      try {
          Bot bot = context.getBean("bot", Bot.class);
          TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
          botsApi.registerBot(bot);
      } catch (TelegramApiException e) {
          e.printStackTrace();
      }
      context.close();
  }
}
