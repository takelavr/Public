package com.rogovig.tgbot.command;

import com.rogovig.tgbot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoCommand implements Command {

    private SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "Для выбора комманды укажите слеш (/). \n"
            + "Чтобы посмотреть доступные команды введите /help";

    public NoCommand (SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);
    }

}
