package io.cutebot.doka2.service.event.level;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.service.InitiateHandler;
import io.cutebot.doka2.service.event.EventListener;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Service
public class NewLevelListener implements EventListener<NewLevelEvent> {

    @Inject
    private EventManager eventManager;

    @Inject
    private InitiateHandler initiateHandler;

    @PostConstruct
    public void init() {
        eventManager.subscribe(this);
    }

    @Override
    public void update(NewLevelEvent event) {
        Hero hero = event.hero;
        SendMessageItem item = new SendMessageItem(hero);
        item.setText("Поздравляем, герой достиг нового уровня!");
        initiateHandler.initiate(SendMessage.items(item));
    }
}
