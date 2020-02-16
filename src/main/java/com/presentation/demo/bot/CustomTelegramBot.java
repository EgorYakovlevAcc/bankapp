package com.presentation.demo.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

@Component
public class CustomTelegramBot extends TelegramLongPollingBot {

    private static Logger TELEGRAM_LOGGER = LoggerFactory.getLogger(CustomTelegramBot.class);

    @Value(value = "${telegram.bot.name}")
    private String botName;

    @Value(value = "${telegram.bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        Message inputMessage = update.getMessage();
        if (Objects.nonNull(inputMessage) && !inputMessage.hasText()) {
            TELEGRAM_LOGGER.info("Get message: " + inputMessage + ". From: " + inputMessage.getAuthorSignature());
            Long chatId = inputMessage.getChatId();
            String inputText = inputMessage.getText();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(inputText).setChatId(chatId);
            try {
                this.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}