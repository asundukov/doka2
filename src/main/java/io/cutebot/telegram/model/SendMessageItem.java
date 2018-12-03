package io.cutebot.telegram.model;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.tgmodel.TgSendPhoto;
import io.cutebot.telegram.tgmodel.TgSendTextMessage;
import io.cutebot.telegram.tgmodel.keyboard.ReplyKeyboardRemove;
import io.cutebot.telegram.tgmodel.keyboard.TgKeyboardButton;
import io.cutebot.telegram.tgmodel.keyboard.TgReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class SendMessageItem {

    private Hero hero;
    private String text = "";
    private boolean inlineAnswer = false;
    private List<List<String>> buttons = new ArrayList<>();

    private String photoPath;

    public SendMessageItem(Hero hero) {
        this.hero = hero;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TgSendTextMessage toTgMessage() {
        TgSendTextMessage tgSendMessage = new TgSendTextMessage();
        tgSendMessage.text = text;
        tgSendMessage.chatId = hero.getHeroId();
        if (!buttons.isEmpty()) {
            tgSendMessage.replyMarkup = createMarkup();
        } else {
            tgSendMessage.replyMarkup = new ReplyKeyboardRemove();
        }
        return tgSendMessage;
    }

    public TgSendPhoto toTgPhoto() {
        TgSendPhoto tgSendPhoto = new TgSendPhoto();
        tgSendPhoto.caption = text;
        tgSendPhoto.chatId = hero.getHeroId();
        tgSendPhoto.photo = photoPath;
        return tgSendPhoto;
    }

    private TgReplyKeyboardMarkup createMarkup() {
        TgReplyKeyboardMarkup keyboard = new TgReplyKeyboardMarkup();
        keyboard.keyboard = new ArrayList<>();
        for (List<String> buttonRow : buttons) {
            List<TgKeyboardButton> inlineButtonRow = new ArrayList<>();
            for (String button : buttonRow) {
                inlineButtonRow.add(new TgKeyboardButton(button));
            }
            keyboard.keyboard.add(inlineButtonRow);
        }
        return keyboard;
    }

    public void setInlineAnswer(boolean inlineAnswer) {
        this.inlineAnswer = inlineAnswer;
    }

    public boolean isInlineAnswer() {
        return inlineAnswer;
    }


    public void setButtons(List<List<String>> buttons) {
        this.buttons = buttons;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isPhoto() {
        return this.photoPath != null;
    }

}
