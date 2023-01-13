package com.rogovig.tgbot.bot;

import com.rogovig.tgbot.command.CommandContainer;
import com.rogovig.tgbot.service.SendBotMessageServiceImpl;
import com.rogovig.tgbot.service.TelegramUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.rogovig.tgbot.command.CommandName.NO;

@Data
@Component
public class TGBot extends  TelegramLongPollingBot{

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    public TGBot(TelegramUserService telegramUserService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return getUsername();
    }

    @Override
    public String getBotToken() {
        return getToken();
    }
}

