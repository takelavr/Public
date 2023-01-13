package com.rogovig.tgbot.command;


import com.rogovig.tgbot.service.SendBotMessageService;
import com.rogovig.tgbot.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService=Mockito.mock(TelegramUserService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService);
    }

    //должен выдать все доступные команды
    @Test
    public void shouldGetAllCommands() {
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    //должен вернуть как неизвестную команду
    @Test
    public void shouldReturnUnknownCommand() {

        String unknowmCommand = "/fffffff";

        Command command = commandContainer.retrieveCommand(unknowmCommand);

        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }

}
