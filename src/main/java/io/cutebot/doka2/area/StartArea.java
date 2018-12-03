package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StartArea implements Area {

    @Inject
    private SelectRaceArea selectRaceArea;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        if (hero.hasHeroName()) {
            return SendMessage.redirect(selectRaceArea);
        }
        hero.setHeroName(input);
        return SendMessage.redirect(selectRaceArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (hero.hasHeroName()) {
            return SendMessage.redirect(selectRaceArea);
        }
        SendMessageItem item = new SendMessageItem(hero);
        item.setText("Привет, друг!\n\n" +
                "Дока 2 - это игра, где ты убиваешь зомби, или ты сам есть зомби, где ты убиваешь самыми изощренными " +
                "способами людей. То есть здесь можно придумать свою определённую местность, то есть можешь " +
                "придумать ту же школу, также её взорвать, убить, но тут более изощрённая ситуация. " +
                "То есть тут не изготавливаешь бомбу, тебе даётся различное оружие, ты можешь трейд делать. " +
                "Почему называется «дока-трейд» — там идёт трейд различными оружиями убийства, уничтожения людей. " +
                "Тут вытаскивают, например, специальное оружие, как вытаскивать кишки в течение 10 минут. " +
                "То есть это вот длится 10 минут.\n\n " +
                "А для начала надо познакомиться, введи свой нейм");
        return SendMessage.items(item);
    }
}
