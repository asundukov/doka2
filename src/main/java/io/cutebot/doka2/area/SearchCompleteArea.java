package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Item;
import io.cutebot.doka2.model.Search;
import io.cutebot.doka2.model.tool.Tool;
import io.cutebot.doka2.model.weapon.Weapon;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;

import static io.cutebot.doka2.model.ItemType.TOOL;
import static io.cutebot.doka2.model.ItemType.WEAPON;
import static java.util.Collections.singletonList;

@Service
public class SearchCompleteArea implements Area {

    private static final Logger log = LoggerFactory.getLogger(SearchCompleteArea.class);

    @Inject
    private MainArea mainArea;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        return SendMessage.redirect(mainArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (!(data instanceof Search)) {
            log.warn("Complete search call with wrong data {}", data);
            return SendMessage.redirect(mainArea);
        }

        Search search = (Search) data;

        SendMessageItem item = new SendMessageItem(hero);
        String message = "Вы вернулись с поисков.\n";
        if (search.getFoundItems().isEmpty()) {
            message += "К сожалению, никаких находок";
            item.setText(message);
            item.setButtons(singletonList(singletonList("Печаль")));
            return SendMessage.items(item);
        } else {
            message += "Найдено:\n";
        }

        for (Item i : search.getFoundItems()) {
            message += i.getFullTitle() + "\n";
        }

        Item bestWeapon = search.getFoundItems().stream()
                .filter(w -> w.getType().equals(WEAPON))
                .max(Comparator.comparing(Item::getModifier))
                .orElse(null);
        Item bestTool = search.getFoundItems().stream()
                .filter(w -> w.getType().equals(TOOL))
                .max(Comparator.comparing(Item::getModifier))
                .orElse(null);

        message += "\n";

        boolean changed = false;

        if (bestWeapon != null && bestWeapon.getModifier() > hero.getWeapon().getModifier()) {
            if (hero.getWeapon().isDropable()) {
                message += "Выброшено: " + hero.getWeapon().getFullTitle() + "\n";
            }
            message += "Одето: " + bestWeapon.getFullTitle() + "\n";
            hero.setWeapon((Weapon) bestWeapon);
            changed = true;
        }

        if (bestTool != null && bestTool.getModifier() > hero.getTool().getModifier()) {
            if (hero.getTool().isDropable()) {
                message += "Выброшено: " + hero.getTool().getFullTitle() + "\n";
            }
            message += "Одето: " + bestTool.getFullTitle() + "\n";
            hero.setTool((Tool) bestTool);
            changed = true;
        }

        if (!changed) {
            message += "Ничего интересного.";
        }

        item.setText(message);
        item.setButtons(singletonList(singletonList("Супер")));

        return SendMessage.items(item);
    }
}
