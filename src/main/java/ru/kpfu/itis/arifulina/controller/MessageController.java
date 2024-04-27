package ru.kpfu.itis.arifulina.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.arifulina.aspect.annotation.HttpRequest;
import ru.kpfu.itis.arifulina.aspect.annotation.Loggable;
import ru.kpfu.itis.arifulina.base.Messages;
import ru.kpfu.itis.arifulina.base.ParamsKey;
import ru.kpfu.itis.arifulina.dto.MessageDto;
import ru.kpfu.itis.arifulina.service.MessageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // global chat
    @Loggable
    @HttpRequest
    @GetMapping(ParamsKey.GLOBAL_CHAT_RM)
    public String getChat() {
        return ParamsKey.GLOBAL_CHAT_VN;
    }

    @Loggable
    @HttpRequest
    @GetMapping(ParamsKey.GLOBAL_CHAT_RM + ParamsKey.HISTORY_RM)
    @ResponseBody
    public List<MessageDto> getGlobalChatHistory() {
        return messageService.getGlobalChatHistory();
    }

    @MessageMapping(ParamsKey.MESSAGE_MAPPING)
    @SendTo(ParamsKey.BROKER_DEST_PREFIX + ParamsKey.MESSAGE_MAPPING)
    @Loggable
    public MessageDto message(MessageDto message) {
        MessageDto serverAnswer = new MessageDto(String.format(Messages.SERVER_HELLO_MSG, message.getSender()), ParamsKey.SERVER_SENDER_NAME);

        messageService.saveMessageToGlobalChat(message);
        messageService.saveMessageToGlobalChat(serverAnswer);
        return serverAnswer;
    }

    // group chats
    @Loggable
    @HttpRequest
    @GetMapping(ParamsKey.GROUP_CHATS_RM)
    public String getGroupChats() {
        return ParamsKey.GROUP_CHATS_VN;
    }

    @Loggable
    @HttpRequest
    @GetMapping(ParamsKey.GROUP_CHATS_RM + ParamsKey.HISTORY_RM + ParamsKey.ROOM_PATH_VAR)
    @ResponseBody
    public List<MessageDto> getGroupChatHistory(@PathVariable(ParamsKey.ROOM_PARAM) Integer code) {
        return messageService.getGroupChatHistory(code);
    }

    @MessageMapping(ParamsKey.ROOM_MESSAGE_MAPPING + ParamsKey.ROOM_PATH_VAR)
    @SendTo(ParamsKey.BROKER_DEST_PREFIX + ParamsKey.ROOM_MESSAGE_MAPPING + ParamsKey.ROOM_PATH_VAR)
    @Loggable
    public MessageDto roomMessage(@DestinationVariable(ParamsKey.ROOM_PARAM) Integer code, MessageDto message) {
        messageService.saveMessageToGroupChat(code, message);
        return message;
    }
}
