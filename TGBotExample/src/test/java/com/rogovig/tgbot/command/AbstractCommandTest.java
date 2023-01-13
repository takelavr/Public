package com.rogovig.tgbot.command;


import com.rogovig.tgbot.bot.TGBot;
import com.rogovig.tgbot.service.SendBotMessageService;
import com.rogovig.tgbot.service.SendBotMessageServiceImpl;
import com.rogovig.tgbot.service.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


//Абстрактный класс для тестирования комманд
abstract class AbstractCommandTest {

    protected TGBot tgBot = Mockito.mock(TGBot.class);
    protected TelegramUserService telegramUserService =Mockito.mock(TelegramUserService.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(tgBot);

    abstract String getCommandName();
    abstract String getCommandMessage();
    abstract Command getCommand();


    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {

        Long chatId = 1111111111L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);


        getCommand().execute(update);

        Mockito.verify(tgBot).execute(sendMessage);




    }

}
