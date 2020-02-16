package com.presentation.demo.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBotsApi getTelegramsBotApi(){
        return new TelegramBotsApi();
    }
}
