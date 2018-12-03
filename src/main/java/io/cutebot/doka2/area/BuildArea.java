package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.BuildSizeType;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static io.cutebot.doka2.model.BuildSizeType.BIG;
import static io.cutebot.doka2.model.BuildSizeType.MEDIUM;
import static io.cutebot.doka2.model.BuildSizeType.SMALL;
import static io.cutebot.doka2.model.BuildSizeType.VERY_SMALL;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class BuildArea implements Area {

    @Inject
    private MainArea mainArea;

    @Inject
    private BuildingArea buildingArea;

    private static final String MESSAGE = "Строительство поможет вам создать места для разрушения и шутинга. " +
            "Разрушать можно и свои и чужие места, но разрушение своих мест даст больше опыта.\n\n" +
            "Какую школу будем строить?";

    private static final String NOTHING = "Ничего";

    private static final List<List<String>> BUTTONS = asList(
            asList(VERY_SMALL.getBtn(), SMALL.getBtn()),
            asList(MEDIUM.getBtn(), BIG.getBtn()),
            singletonList(NOTHING)
    );

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        if (input.equals(NOTHING)) {
            return SendMessage.redirect(mainArea);
        }
        if (BuildSizeType.getByBtnTitle(input) != null) {
            return SendMessage.redirect(buildingArea, BuildSizeType.getByBtnTitle(input));
        }
        SendMessageItem item = new SendMessageItem(hero);
        item.setText(MESSAGE);
        item.setButtons(BUTTONS);
        return SendMessage.items(item);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        SendMessageItem item = new SendMessageItem(hero);
        item.setText(MESSAGE);
        item.setButtons(BUTTONS);
        return SendMessage.items(item);
    }
}
