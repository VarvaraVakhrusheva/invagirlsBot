package org.vakhrusheva;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.vakhrusheva.commands.CommandsConfig;
import org.vakhrusheva.commands.HelpCommand;
import org.vakhrusheva.commands.NonCommand;
import org.vakhrusheva.commands.StartCommand;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:bot.properties")
public class Bot extends TelegramLongPollingCommandBot {
  @Value("${bot.name}")
  private String BOT_NAME;

  @Value("${bot.token}")
  private String BOT_TOKEN;

  private final NonCommand nonCommand;

  public Bot(DefaultBotOptions options) {
    super(options);
    this.nonCommand = new NonCommand();
    register(new StartCommand("start", "Старт"));
    register(new HelpCommand("help", "Помощь"));
  }

  @Override
  public String getBotToken() {
    return BOT_TOKEN;
  }

  @Override
  public String getBotUsername() {
    return BOT_NAME;
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    Message msg = update.getMessage();
    Long chatId;
    try {
      chatId = msg.getChatId();
    } catch (NullPointerException e) {
      chatId = update.getCallbackQuery().getMessage().getChatId();
    }
    if (update.hasCallbackQuery()) {
      try {
        sendCallbackButtonQuery(
            msg, update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery());
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
    SendMessage answer = nonCommand.nonCommandExecute(chatId, msg.getText());
    try {
      execute(answer);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private void sendCallbackButtonQuery(Message msg, Long chatId, CallbackQuery callbackQuery)
      throws TelegramApiException {
    if (callbackQuery.getData().startsWith("Есть 18")) {
      InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
      InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
      inlineKeyboardButton1.setText("Да");
      inlineKeyboardButton1.setCallbackData(CommandsConfig.getAgree());
      inlineKeyboardButton2.setText("Нет");
      inlineKeyboardButton2.setCallbackData(CommandsConfig.getNotAgree());
      List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
      List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
      keyboardButtonsRow1.add(inlineKeyboardButton1);
      keyboardButtonsRow2.add(inlineKeyboardButton2);
      List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
      rowList.add(keyboardButtonsRow1);
      rowList.add(keyboardButtonsRow2);
      inlineKeyboardMarkup.setKeyboard(rowList);
      SendMessage answer = new SendMessage();
      answer.setChatId(String.valueOf(chatId));
      answer.setText(CommandsConfig.getIntroduction());
      answer.setReplyMarkup(inlineKeyboardMarkup);
      execute(answer);
    } else if (callbackQuery.getData().startsWith("Нет 18")) {
      SendMessage answer = new SendMessage();
      answer.setChatId(String.valueOf(chatId));
      answer.setText(CommandsConfig.getUnder18Response());
      execute(answer);
    } else if (callbackQuery.getData().startsWith(CommandsConfig.getAgree())) {
      SendMessage answer = new SendMessage();
      answer.setChatId(String.valueOf(chatId));
      answer.setText(CommandsConfig.getRules());
      InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
      InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
      inlineKeyboardButton1.setText("Да");
      inlineKeyboardButton1.setCallbackData("Продолжаем");
      inlineKeyboardButton2.setText("Нет");
      inlineKeyboardButton2.setCallbackData(CommandsConfig.getNotAgree());
      List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
      List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
      keyboardButtonsRow1.add(inlineKeyboardButton1);
      keyboardButtonsRow2.add(inlineKeyboardButton2);
      List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
      rowList.add(keyboardButtonsRow1);
      rowList.add(keyboardButtonsRow2);
      inlineKeyboardMarkup.setKeyboard(rowList);
      answer.setReplyMarkup(inlineKeyboardMarkup);
      execute(answer);
    } else if (callbackQuery.getData().startsWith("Продолжаем")) {
      SendMessage answer = new SendMessage();
      answer.setChatId(String.valueOf(chatId));
      // answer.setText(CommandsConfig.getInvitation());
      answer.setText(CommandsConfig.getWhatIsProhibited());
      InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
      InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
      inlineKeyboardButton1.setText("Да");
      inlineKeyboardButton1.setCallbackData("Далее продолжаем");
      inlineKeyboardButton2.setText("Нет");
      inlineKeyboardButton2.setCallbackData(CommandsConfig.getNotAgree());
      List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
      List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
      keyboardButtonsRow1.add(inlineKeyboardButton1);
      keyboardButtonsRow2.add(inlineKeyboardButton2);
      List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
      rowList.add(keyboardButtonsRow1);
      rowList.add(keyboardButtonsRow2);
      inlineKeyboardMarkup.setKeyboard(rowList);
      answer.setReplyMarkup(inlineKeyboardMarkup);
      execute(answer);
    } else if (callbackQuery.getData().startsWith("Далее продолжаем")) {
      SendMessage answer = new SendMessage();
      answer.setChatId(String.valueOf(chatId));
      // answer.setText(CommandsConfig.getInvitation());
      answer.setText(CommandsConfig.getCare());
      InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
      InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
      inlineKeyboardButton1.setText("Да");
      inlineKeyboardButton1.setCallbackData("Финал");
      inlineKeyboardButton2.setText("Нет");
      inlineKeyboardButton2.setCallbackData(CommandsConfig.getNotAgree());
      List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
      List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
      keyboardButtonsRow1.add(inlineKeyboardButton1);
      keyboardButtonsRow2.add(inlineKeyboardButton2);
      List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
      rowList.add(keyboardButtonsRow1);
      rowList.add(keyboardButtonsRow2);
      inlineKeyboardMarkup.setKeyboard(rowList);
      answer.setReplyMarkup(inlineKeyboardMarkup);
      execute(answer);
    } else if (callbackQuery.getData().startsWith("Финал")) {
      SendMessage answer = new SendMessage();
      answer.setChatId(String.valueOf(chatId));
      answer.setText(CommandsConfig.getInvitation());
      execute(answer);
    } else if (callbackQuery.getData().startsWith("Вступаем в чат")) {

    } else if (callbackQuery.getData().startsWith(CommandsConfig.getNotAgree()) ||
            callbackQuery.getData().startsWith("Завершаем")) {
      SendMessage answer = new SendMessage();
      answer.setChatId(String.valueOf(chatId));
      answer.setText(CommandsConfig.getNotAgreedResponse());
      execute(answer);
    }
  }
}
