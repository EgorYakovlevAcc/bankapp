package com.presentation.demo.bot;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class CustomTelegramBot extends TelegramLongPollingBot {

    private static Logger TELEGRAM_LOGGER = LoggerFactory.getLogger(CustomTelegramBot.class);

    @Value(value = "${telegram.bot.name}")
    private String botName;

    @Value(value = "${telegram.bot.token}")
    private String botToken;

    @Value(value = "${telegram.bot.proxy}")
    private Boolean proxyEnabled;

    @Value(value = "${telegram.bot.proxyHost}")
    private String proxyHost;

    @Value(value = "${telegram.bot.proxyPort}")
    private Integer proxyPort;

    @Autowired
    private TelegramBotsApi telegramBotsApi;

    private DefaultBotOptions botOptions;

    DefaultBotOptions getBotOptions(){
        return botOptions;
    }

    void setBotOptions(DefaultBotOptions botOptions){//no way to put it into superclass
        this.botOptions = botOptions;
    }

    @PostConstruct
    public void register(){
        try{
            DefaultBotOptions myBotOptions = ApiContext.getInstance(DefaultBotOptions.class);
            if (proxyEnabled) {
                myBotOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
                HttpHost httpHost = new HttpHost(proxyHost, proxyPort);
                RequestConfig myConfig = RequestConfig.custom().setProxy(httpHost).build();
                myBotOptions.setRequestConfig(myConfig);
            }
            this.setBotOptions(myBotOptions);
            telegramBotsApi.registerBot(this);
        }
        catch (TelegramApiException apiExc){
            apiExc.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message inputMessage = update.getMessage();
        TELEGRAM_LOGGER.info("Here");
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