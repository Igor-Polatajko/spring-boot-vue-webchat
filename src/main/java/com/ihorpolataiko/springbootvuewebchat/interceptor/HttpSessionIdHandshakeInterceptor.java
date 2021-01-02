package com.ihorpolataiko.springbootvuewebchat.interceptor;

import com.ihorpolataiko.springbootvuewebchat.dto.OutgoingMessage;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Optional;

import static com.ihorpolataiko.springbootvuewebchat.util.Constants.USERNAME;

@Component
public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

//    private SimpMessageSendingOperations messagingTemplate;
//
//    public HttpSessionIdHandshakeInterceptor(SimpMessageSendingOperations messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            Optional.ofNullable(servletRequest.getServletRequest().getSession())
                    .ifPresent(session -> {
                        String username = (String) session.getAttribute(USERNAME);
                        attributes.put(USERNAME, username);

                      //  notifyOfJoining(username);
                    });

            return true;
        }

        return false;
    }

//    private void notifyOfJoining(String username) {
//        OutgoingMessage joinNotificationMessage = OutgoingMessage.builder()
//                .sender("Chat info")
//                .content(String.format("%s joined the chat", username))
//                .build();
//        messagingTemplate.convertAndSend(
//                "/chat/messages", joinNotificationMessage);
//    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler webSocketHandler, Exception ex) {

    }

}
