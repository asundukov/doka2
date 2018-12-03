package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Race;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class SelectRaceArea implements Area {

    private static final List<List<String>> BUTTONS = new ArrayList<>(Race.values().length);
    static {
        asList(Race.values()).forEach(race -> BUTTONS.add(singletonList(race.getFullTitle())));
    }

    @Inject
    private MainArea mainArea;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        if(hero.hasRace()) {
            return SendMessage.redirect(mainArea);
        }
        if (Race.getByFullTitle(input) != null) {
            hero.setRace(Race.getByFullTitle(input));
            return SendMessage.redirect(mainArea);
        }
        return heroIsComing(hero, "Камон, выбери нормально");
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (hero.hasRace()) {
            return SendMessage.redirect(mainArea);
        }
        SendMessageItem item = new SendMessageItem(hero);
        item.setText("Кто ты?");
        if (data instanceof String) {
            String overload = (String) data;
            if (!overload.isEmpty()) {
                item.setText(overload);
            }
        }
        item.setButtons(BUTTONS);
        return SendMessage.items(item);
    }
}
