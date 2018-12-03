package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.service.build.CompletedBuildsService;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class BuildingCompleteArea implements Area {

    private static final Logger log = LoggerFactory.getLogger(BuildingCompleteArea.class);
    private static final String DESTROY_IT = "\uD83D\uDCA3 Разрушить";
    private static final String GO_AWAY = "\uD83D\uDC63 Уйти";

    @Inject
    private MainArea mainArea;

    @Inject
    private DestroyingArea destroyingArea;

    @Inject
    private CompletedBuildsService buildService;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        if (input.equals(DESTROY_IT)) {
            SendMessageItem item = new SendMessageItem(hero);
            Build build = buildService.findAndDestroyLastCompleted(hero);
            if (build == null) {
                item.setText("Ой! Вы не успели разрушить свою постройку =\\");
                item.setButtons(singletonList(singletonList(GO_AWAY)));
                return SendMessage.items(item);
            }
            return SendMessage.redirect(destroyingArea, build);
        }
        return SendMessage.redirect(mainArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (!(data instanceof Build)) {
            log.warn("Wrong call coming for complete building. Data is {} ", data);
            return SendMessage.redirect(mainArea);
        }
        Build completedBuilding = (Build) data;
        SendMessageItem item = new SendMessageItem(completedBuilding.getHero());
        item.setText("Стройка завершена.\n" +
                "Упорным трудом вы построили школу размером в " + completedBuilding.getSize() +
                " и получили " + completedBuilding.getGainedExp() + " опыта. \n" +
                "Чтоб ее разрушить понадобится " + completedBuilding.getBuildSizeType().getTimeStr() + "\n" +
                "Что будем делать?");
        item.setButtons(singletonList(asList(DESTROY_IT, GO_AWAY)));
        return SendMessage.items(item);
    }

}
