package org.vakhrusheva;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.vakhrusheva.commands.HelpCommand;
import org.vakhrusheva.commands.NonCommand;
import org.vakhrusheva.commands.StartCommand;

@Configuration
@PropertySource("classpath:bot.properties")
public class BotConfig {

  @Bean
  public Bot bot() {
    return new Bot(new DefaultBotOptions());
  }

  @Bean
  public HelpCommand helpCommand() {
    return new HelpCommand("help", "Помощь");
  }

  @Bean
  public NonCommand nonCommand() {
    return new NonCommand();
  }

  @Bean
  public StartCommand startCommand() {
    return new StartCommand("start", "Старт");
  }
}
