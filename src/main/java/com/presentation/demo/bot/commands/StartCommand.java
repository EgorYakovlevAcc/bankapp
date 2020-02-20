package com.presentation.demo.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {

    private static final Logger STARTER_LOGGER = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand() {
        super("start", "Starter command");
    }

    @Override
    public void execute(AbsSender absSender, User botUser, Chat chat, String[] arguments) {
        try {
            STARTER_LOGGER.info("HERE");
            SendMessage message = new SendMessage();
            message.setChatId(chat.getId());
            message.setText(String.format("Welcome to NCBestBankBot, %s.\n I will help you to know everything about your bank account!","User"));
            absSender.execute(message);
        } catch (TelegramApiException e) {
            STARTER_LOGGER.info(e.getLocalizedMessage());
        }
    }
}

