package com.presentation.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Pattern;

@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotConfig {

    private String token;

    @Pattern(regexp = "bot$")
    private String name;

    public String getToken() {
        return token;
    }

    public void setToken(String auth_token) {
        this.token = auth_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
