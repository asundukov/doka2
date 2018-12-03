package io.cutebot.telegram.model;

import io.cutebot.doka2.Hero;
import org.springframework.stereotype.Service;

@Service
public class SendMessageFactory {
    public SendMessageItem createSendMessage(Hero hero) {
        return new SendMessageItem(hero);
    }
}
