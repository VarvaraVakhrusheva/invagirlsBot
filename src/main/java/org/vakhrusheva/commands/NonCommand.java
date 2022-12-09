package org.vakhrusheva.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class NonCommand {
    public SendMessage nonCommandExecute(Long chatId, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Да");
        inlineKeyboardButton1.setCallbackData("Есть 18");
        inlineKeyboardButton2.setText("Нет");
        inlineKeyboardButton2.setCallbackData("Нет 18");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        KeyboardButton button1 = new KeyboardButton();
        button1.setText("Да");
        KeyboardButton button2 = new KeyboardButton();
        button2.setText("Нет");
        KeyboardRow row1 = new KeyboardRow();
        row1.add(button1);
        row1.add(button2);
        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .keyboardRow(row1)
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true).build();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(false);
        return getAnswer(chatId, text, inlineKeyboardMarkup);
    }

    private SendMessage getAnswer(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage answer = new SendMessage();
        answer.setChatId(String.valueOf(chatId));
        answer.setText("Очень приятно, " + text + "!\n\nВам уже исполнилось 18 лет?");
        answer.setReplyMarkup(inlineKeyboardMarkup);
        return answer;
    }
}

