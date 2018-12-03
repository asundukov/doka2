package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgSendPhoto extends TgSendMessage {
    public String caption;

    @JsonProperty("parse_mode")
    public String parseMode = "HTML";

    public String photo;

    @Override
    public String toString() {
        return "TgSendPhoto{" +
                "caption='" + caption + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
