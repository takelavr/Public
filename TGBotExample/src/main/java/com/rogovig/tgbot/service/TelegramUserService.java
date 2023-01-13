package com.rogovig.tgbot.service;

import com.rogovig.tgbot.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {

    //сохраняет в БД юзеров
    void save(TelegramUser telegramUser);

    //список активных юзеров
    List<TelegramUser>retrieveAllActiveUsers();

    //для поиска юзера по chat_Id
    Optional<TelegramUser>findByChatId(String chatId);
}
