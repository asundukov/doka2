package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.model.BuildSizeType;
import io.cutebot.doka2.service.build.BuildInProgressService;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BuildingArea implements Area {

    private final static Logger log = LoggerFactory.getLogger(BuildingArea.class);

    @Inject
    private BuildArea buildArea;

    @Inject
    private BuildInProgressService buildInProgressService;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        Build build = buildInProgressService.getByHero(hero);
        if (build != null) {
            SendMessageItem item = new SendMessageItem(hero);
            item.setText("Стройка идет полным ходом!\n" +
                    "Будет заверешена примерно через: " + build.getTimeRemaining() + "\n\n" +
                    "А пока можно расслабиться, попить чайку и пофлудить в @doka2_chat, " +
                    "посмотреть статистику города /world, героя /hero или почитать что вообще " +
                    "тут происходит /help");
            return SendMessage.items(item);
        }
        return SendMessage.redirect(buildArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (!(data instanceof BuildSizeType)) {
            log.warn("Wrong call building area with data {}", data);
            return SendMessage.redirect(buildArea);
        }
        BuildSizeType sizeType = (BuildSizeType) data;

        buildInProgressService.createNewBuild(hero, sizeType);

        SendMessageItem item = new SendMessageItem(hero);
        item.setText("Вы начали строить.");
        return SendMessage.items(item);
    }

}
