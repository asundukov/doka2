package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.model.SendMessage;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class NoArea implements Area {

    @Inject
    private StartArea startArea;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        return SendMessage.redirect(startArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        return SendMessage.redirect(startArea);
    }
}
