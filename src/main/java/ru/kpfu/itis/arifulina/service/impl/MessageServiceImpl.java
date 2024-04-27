package ru.kpfu.itis.arifulina.service.impl;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.arifulina.dto.MessageDto;
import ru.kpfu.itis.arifulina.service.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    // some kind of stub for repository
    private static final Map<Integer, List<MessageDto>> groupChatsHistory = new HashMap<>();
    private static final List<MessageDto> globalChatHistory = new ArrayList<>();

    @Override
    public List<MessageDto> getGroupChatHistory(Integer code) {
        return groupChatsHistory.get(code);
    }
    @Override
    public void saveMessageToGroupChat(Integer code, MessageDto message) {
        if (groupChatsHistory.containsKey(code)) {
            groupChatsHistory.get(code).add(message);
        } else {
            groupChatsHistory.put(code, new ArrayList<>(List.of(message)));
        }
    }

    @Override
    public List<MessageDto> getGlobalChatHistory() {
        return globalChatHistory;
    }

    @Override
    public void saveMessageToGlobalChat(MessageDto message) {
        globalChatHistory.add(message);
    }
}
