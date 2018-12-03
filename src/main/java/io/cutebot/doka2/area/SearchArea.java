package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Race;
import io.cutebot.doka2.model.SearchType;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class SearchArea implements Area {

    @Inject
    private MainArea mainArea;

    @Inject
    private SearchingArea searchingArea;

    private static final String MESSAGE = "Поиск вещей в городских лесах поможет вам найти предметы для " +
            "более эффективных строительных или карательных операций. Эффективность поиска тем лучше, чем более " +
            "высокий уровень у вашего персонажа. ";

    private static final String NOTHING = "Не будем ща искать";

    private static final List<List<String>> BUTTONS = new ArrayList<>(Race.values().length + 1);
    static {
        asList(SearchType.values()).forEach(searchType -> BUTTONS.add(singletonList(searchType.getBtnTitle())));
        BUTTONS.add(singletonList(NOTHING));
    }

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        if (input.equals(NOTHING)) {
            return SendMessage.redirect(mainArea);
        }
        if (SearchType.getByBtnTitle(input) != null) {
            return SendMessage.redirect(searchingArea, SearchType.getByBtnTitle(input));
        }
        return heroIsComing(hero, "");
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        SendMessageItem item = new SendMessageItem(hero);
        item.setText(MESSAGE);
        item.setButtons(BUTTONS);
        return SendMessage.items(item);
    }
}
