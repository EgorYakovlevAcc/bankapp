package com.presentation.demo.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Configuration
@ConfigurationProperties(value = "telegram.bot")
public class TelegramBotConfig {

    @Bean
    public TelegramBotsApi getTelegramBotsApi(){
        return new TelegramBotsApi();
    }

    private String token;

    private String name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
