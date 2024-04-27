package ru.kpfu.itis.arifulina.service;

import ru.kpfu.itis.arifulina.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getGroupChatHistory(Integer code);
    void saveMessageToGroupChat(Integer code, MessageDto message);
    List<MessageDto> getGlobalChatHistory();
    void saveMessageToGlobalChat(MessageDto message);
}
