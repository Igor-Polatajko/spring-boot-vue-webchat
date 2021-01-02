package com.ihorpolataiko.springbootvuewebchat.controller;

import com.ihorpolataiko.springbootvuewebchat.dto.IncomingMessage;
import com.ihorpolataiko.springbootvuewebchat.dto.OutgoingMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import static com.ihorpolataiko.springbootvuewebchat.util.Constants.USERNAME;
import static java.util.Objects.isNull;

// ToDo implement notification of new joins
// ToDo implement list of current participants
// ToDo optionally, implement couple of chats
@Controller
public class WebSocketController {

    private SimpMessageSendingOperations messagingTemplate;

    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    public void handleMessage(IncomingMessage incomingMessage, SimpMessageHeaderAccessor headerAccessor) {

        Object senderAttribute = headerAccessor.getSessionAttributes().get(USERNAME);
        if (isNull(senderAttribute)) {
            return;
        }

        if (StringUtils.isBlank(incomingMessage.getContent())) {
            return;
        }

        OutgoingMessage outgoingMessage = OutgoingMessage.builder()
                .content(incomingMessage.getContent())
                .sender((String) senderAttribute)
                .build();

        messagingTemplate.convertAndSend("/chat/messages", outgoingMessage);
    }

}
