package io.cutebot.doka2.service.build;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.area.DestroyCompleteArea;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.service.BuildService;
import io.cutebot.doka2.service.InitiateHandler;
import io.cutebot.doka2.service.event.EventListener;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.doka2.service.event.building.BuildFinishDestroyEvent;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Service
public class DestroyedBuildsService implements EventListener<BuildFinishDestroyEvent> {

    @Inject
    private EventManager eventManager;

    @Inject
    private DestroyCompleteArea destroyCompleteArea;

    @Inject
    private InitiateHandler initiateHandler;

    @Inject
    private BuildService buildService;

    @PostConstruct
    public void init() {
        eventManager.subscribe(this);
    }

    @Override
    public void update(BuildFinishDestroyEvent event) {
        Build build = event.destroyedBuild;

        build.calcDestroy();

        buildService.completeDestroy(build);

        Hero destroyer = build.getDestroyer();
        destroyer.setArea(destroyCompleteArea);
        initiateHandler.initiate(destroyer.comingArea(build));
    }
}
