package com.presentation.demo.bot.handlers;

import com.presentation.demo.bot.commands.StartCommand;
import com.presentation.demo.bot.config.TelegramBotConfig;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

//@Component
public class CommandHandler extends TelegramLongPollingCommandBot {

    private static final Logger COMMAND_HANDLER_LOGGER = LoggerFactory.getLogger(CommandHandler.class);

    @Autowired
    private TelegramBotsApi telegramBotsApi;

    @Autowired
    private TelegramBotConfig telegramBotConfig;

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
            Boolean registered = register(new StartCommand());
            COMMAND_HANDLER_LOGGER.info(registered.toString());
            DefaultBotOptions myBotOptions = ApiContext.getInstance(DefaultBotOptions.class);
            if (telegramBotConfig.getProxy()) {
                myBotOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
                HttpHost httpHost = new HttpHost(telegramBotConfig.getProxyHost(), telegramBotConfig.getProxyPort());
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
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        if  (Objects.nonNull(message) && message.hasText()) {
            COMMAND_HANDLER_LOGGER.info("Get message: " + message + ". From: " + message.getAuthorSignature());
            SendMessage echoMessage = new SendMessage();
            echoMessage.setChatId(message.getChatId());
            echoMessage.setText("Echo:\n" + message.getText());

            try {
                execute(echoMessage);
            } catch (TelegramApiException e) {
                COMMAND_HANDLER_LOGGER.info(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getToken();
    }
}
