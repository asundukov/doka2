package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.model.BuildSizeType;
import io.cutebot.doka2.service.build.CompletedBuildsService;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static io.cutebot.doka2.model.BuildSizeType.BIG;
import static io.cutebot.doka2.model.BuildSizeType.MEDIUM;
import static io.cutebot.doka2.model.BuildSizeType.SMALL;
import static io.cutebot.doka2.model.BuildSizeType.VERY_SMALL;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class DestroyArea implements Area {

    @Inject
    private MainArea mainArea;

    @Inject
    private CompletedBuildsService completedBuildsService;

    @Inject
    private DestroyingArea destroyingArea;

    private static final String NOTHING = "Ничего";

    private static final List<List<String>> BUTTONS = asList(
            asList(VERY_SMALL.getBtn(), SMALL.getBtn()),
            asList(MEDIUM.getBtn(), BIG.getBtn()),
            singletonList(NOTHING)
    );

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        if (BuildSizeType.getByBtnTitle(input) != null) {
            SendMessageItem item = new SendMessageItem(hero);
            Build build = completedBuildsService.findAndDestroy(hero, BuildSizeType.getByBtnTitle(input));
            if (build == null) {
                item.setText("Не осталось строений выбранного размера, что-нибудь еще?\n\n" + availableSizes());
                item.setButtons(BUTTONS);
                return SendMessage.items(item);
            }
            return SendMessage.redirect(destroyingArea, build);
        }
        return SendMessage.redirect(mainArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {

        SendMessageItem item = new SendMessageItem(hero);
        String message = availableSizes() + "\nЧе разрушаем?";
        item.setText(message);
        item.setButtons(BUTTONS);
        return SendMessage.items(item);
    }

    private String availableSizes() {
        Map<BuildSizeType, Integer> countsBySizes = completedBuildsService.getCompletedCountsBySizes();
        StringBuilder msg = new StringBuilder("В городе доступно:\n");
        for (BuildSizeType type : BuildSizeType.values()) {
            msg.append(type.getCountTitle()).append(": ").append(countsBySizes.get(type)).append("\n");
        }
        return msg.toString();
    }
}
