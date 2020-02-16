package com.presentation.demo.bot;

import com.presentation.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Objects;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private UserService userService;
    @Override
    public void onUpdateReceived(Update update) {
        Message inputMessage = update.getMessage();
        if (Objects.nonNull(inputMessage) && !inputMessage.hasText()) {
            Long chatId = inputMessage.getChatId();
            String inputText = inputMessage.getText();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(inputText).setChatId(chatId);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "ncbank_bot";
    }

    @Override
    public String getBotToken() {
        return "1067096104:AAHQqloGGUP5k7DkzGK3mpvpCp3krJ25uLM";
    }
}
