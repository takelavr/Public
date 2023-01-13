package com.rogovig.tgbot.service;

import com.rogovig.tgbot.bot.TGBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



//отправщик сообщений
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TGBot tgBot;

    @Autowired
    public SendBotMessageServiceImpl(TGBot tgBot) {
        this.tgBot = tgBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            tgBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
