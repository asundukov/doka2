package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgSendMessage {
    @JsonProperty("chat_id")
    public Long chatId;
}
