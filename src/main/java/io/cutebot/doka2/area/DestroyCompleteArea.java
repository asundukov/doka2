package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Build;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class DestroyCompleteArea implements Area {
    private static final Logger log = LoggerFactory.getLogger(DestroyCompleteArea.class);
    @Inject
    private MainArea mainArea;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        return SendMessage.redirect(mainArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (!(data instanceof Build)) {
            log.warn("Complete destroy call with wrong data {}", data);
            return SendMessage.redirect(mainArea);
        }
        Build build = (Build) data;
        SendMessageItem item = new SendMessageItem(hero);
        item.setText("Вы разрушили здание и получили " + build.getGainedDestroyExp() +  " опыта.");
        item.setButtons(Collections.singletonList(Collections.singletonList("\uD83D\uDCAA\uD83C\uDFFC")));
        return SendMessage.items(item);
    }
}
