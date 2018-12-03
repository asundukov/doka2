package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class MainArea implements Area {

    @Inject
    private BuildArea buildArea;

    @Inject
    private DestroyArea destroyArea;

    @Inject
    private SearchArea searchArea;

    private final static String MESSAGE = "%heroName%, вы стоите посреди небольшой площади вашего города. " +
            "Перед вами необъятный мир, но внутренне вы понимаете что вам интересно только " +
            "строить школы, искать девайсы и... убивать.\n" +
            "\n" +
            "<b>Герой %heroShortRace% %heroName%</b>\n" +
            "⭐️ %level% ур.\n" +
            "\uD83D\uDCAA %strength% \n" +
            "\uD83E\uDD38\u200D♂️%agility% \n" +
            "<b>Инструменты и оружие</b>\n" +
            "\uD83D\uDD28 %tool%\n" +
            "\uD83D\uDD2B %weapon%\n" +
            "<b>Площадь</b>\n" +
            "%heroCounts%" +
            "\nЧем займемся?";

    private static final String BUILD_SCHOOL = "\uD83D\uDEE0 Создать школу";
    private static final String SEARCH_ITEMS = "\uD83D\uDC40 Искать предметы";
    private static final String DESTROY = "\uD83D\uDCA3 Разрушать";

    private static final List<List<String>> buttons = asList(
            singletonList(BUILD_SCHOOL),
            singletonList(SEARCH_ITEMS),
            singletonList(DESTROY)
    );

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        SendMessageItem item = new SendMessageItem(hero);
        if (input.equals(BUILD_SCHOOL)) {
            return SendMessage.redirect(buildArea);
        }
        if (input.equals(DESTROY)) {
            return SendMessage.redirect(destroyArea);
        }
        if (input.equals(SEARCH_ITEMS)) {
            return SendMessage.redirect(searchArea);
        }
        item.setText(getFormattedMessage(hero));
        item.setButtons(buttons);
        return SendMessage.items(item);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        SendMessageItem item = new SendMessageItem(hero);
        item.setText(getFormattedMessage(hero));
        item.setButtons(buttons);
        return SendMessage.items(item);
    }

    private String getFormattedMessage(Hero hero) {
        return MESSAGE
                .replace("%heroName%", hero.getHeroName())
                .replace("%tool%", hero.getTool().getFullTitle())
                .replace("%weapon%", hero.getWeapon().getFullTitle())
                .replace("%level%", Integer.valueOf(hero.getLevel()).toString())
                .replace("%strength%", Integer.valueOf(hero.getStrength()).toString())
                .replace("%agility%", Integer.valueOf(hero.getAgility()).toString())
                .replace("%heroShortRace%", hero.getRace().shortTitle());

    }
}
