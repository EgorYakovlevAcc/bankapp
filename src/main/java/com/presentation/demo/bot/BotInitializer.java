package com.presentation.demo.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Component
public class BotInitializer {
    @Autowired
    private Bot bot;
    @Autowired
    private TelegramBotsApi telegramBotsApi;

    public void initBot() {
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
