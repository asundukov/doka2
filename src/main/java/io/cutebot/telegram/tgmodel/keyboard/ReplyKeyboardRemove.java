package io.cutebot.telegram.tgmodel.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplyKeyboardRemove extends TgKeyboard {
    @JsonProperty("remove_keyboard")
    public boolean removeKeyboard = true;
}
