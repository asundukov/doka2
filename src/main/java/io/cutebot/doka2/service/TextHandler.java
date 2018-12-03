package io.cutebot.doka2.service;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.tgmodel.TgMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TextHandler {
    private static final Logger log = LoggerFactory.getLogger(TextHandler.class);

    public SendMessage handle(Hero hero, TgMessage message) {
        String text = message.text;
        return hero.handleInput(text);
    }

}
