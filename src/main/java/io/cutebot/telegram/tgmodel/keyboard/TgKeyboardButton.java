package io.cutebot.telegram.tgmodel.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgKeyboardButton {
    @JsonProperty("text")
    public String text;

    public TgKeyboardButton(String text) {
        this.text = text;
    }
}
