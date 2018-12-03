package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.model.SendMessage;

public interface Area {
    SendMessage handleInput(Hero hero, String input);
    SendMessage heroIsComing(Hero hero, Object data);
}
