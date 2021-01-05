package com.ihorpolataiko.springbootvuewebchat.listener;

import com.ihorpolataiko.springbootvuewebchat.dto.Participant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ihorpolataiko.springbootvuewebchat.util.Constants.USERNAME;

@Component
@Slf4j
public class WebSocketConnectionsListener {

    private Map<String, Participant> currentConnections = new ConcurrentHashMap<>();

    private SimpMessageSendingOperations messagingTemplate;

    public WebSocketConnectionsListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener(SessionSubscribeEvent.class)
    public void handleWebsocketConnectListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("Received a new web socket subscription. sessionId: {}, destination: {}",
                headerAccessor.getSessionId(), headerAccessor.getDestination());

        if (StringUtils.equals("/chat/messages", headerAccessor.getDestination())) {

            String username = (String) headerAccessor.getSessionAttributes().get(USERNAME);
            currentConnections.put(headerAccessor.getSessionId(), Participant.builder().username(username).build());
        }

        messagingTemplate.convertAndSend("/chat/participants", currentConnections.values());

    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("Session closed. sessionId: {}", headerAccessor.getSessionId());

        currentConnections.remove(headerAccessor.getSessionId());

        messagingTemplate.convertAndSend("/chat/participants", currentConnections.values());
    }

}
