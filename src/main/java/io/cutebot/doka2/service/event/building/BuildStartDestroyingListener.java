package io.cutebot.doka2.service.event.building;

import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.service.InitiateHandler;
import io.cutebot.doka2.service.event.EventListener;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static java.util.Collections.singletonList;

@Service
public class BuildStartDestroyingListener implements EventListener<BuildStartDestroyingEvent> {

    @Inject
    private InitiateHandler initiateHandler;

    @Inject
    private EventManager eventManager;

    @PostConstruct
    public void init() {
        eventManager.subscribe(this);
    }

    @Override
    public void update(BuildStartDestroyingEvent event) {
        Build build = event.build;
        if (build.getDestroyer().equals(build.getHero())) {
            return;
        }

        SendMessageItem item = new SendMessageItem(build.getHero());
        item.setText("К сожалению, твою крутую постройку начал разрушать " +
                "<b>" + build.getDestroyer().getFullHeroName() + "</b>");
        item.setButtons(singletonList(singletonList("Печаль")));
        initiateHandler.initiate(SendMessage.items(item));
    }
}
