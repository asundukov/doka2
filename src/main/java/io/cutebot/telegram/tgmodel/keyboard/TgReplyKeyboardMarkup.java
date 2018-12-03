package io.cutebot.telegram.tgmodel.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TgReplyKeyboardMarkup extends TgKeyboard {
    public List<List<TgKeyboardButton>> keyboard;

    @JsonProperty("resize_keyboard")
    public boolean resizeKeyboard = true;

    @JsonProperty("one_time_keyboard")
    public boolean oneTimeKeyboard = true;

    @JsonProperty("selective")
    public boolean selective = false;
}
