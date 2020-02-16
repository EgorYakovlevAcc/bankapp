package com.presentation.demo.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotInitializer {

    @Autowired
    private CustomTelegramBot customTelegramBot;

    @Autowired
    private TelegramBotsApi telegramBotsApi;

    public void initBot(){
        try{
            telegramBotsApi.registerBot(customTelegramBot);
        }
        catch (TelegramApiException apiExc){
            apiExc.printStackTrace();
        }
    }

}
