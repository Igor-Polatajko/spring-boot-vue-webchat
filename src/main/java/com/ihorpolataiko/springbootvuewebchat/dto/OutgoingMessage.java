package com.ihorpolataiko.springbootvuewebchat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingMessage {

    private String sender;

    private String content;

}
