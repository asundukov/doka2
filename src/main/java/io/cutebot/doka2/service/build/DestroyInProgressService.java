package io.cutebot.doka2.service.build;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.area.DestroyingArea;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.service.BuildService;
import io.cutebot.doka2.service.event.EventListener;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.doka2.service.event.building.BuildFinishDestroyEvent;
import io.cutebot.doka2.service.event.building.BuildStartDestroyEvent;
import io.cutebot.doka2.service.event.building.BuildStartDestroyingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.Semaphore;

@Service
public class DestroyInProgressService implements EventListener<BuildStartDestroyEvent> {
    private static final Logger log = LoggerFactory.getLogger(DestroyInProgressService.class);
    @Inject
    private BuildService buildService;
    @Inject
    private EventManager eventManager;
    @Inject
    private DestroyingArea destroyingArea;

    private Map<Hero, Build> heroDestroys = new HashMap<>();
    private TreeSet<Build> destroys = new TreeSet<>(new Build.DestroyFinishTimeComparator());

    private Build nextDestroyedBuild;

    private Semaphore s = new Semaphore(1);

    @PostConstruct
    public void init() {
        eventManager.subscribe(this);

        List<Build> allActiveDestroys = buildService.getActiveDestroys();
        destroys.addAll(allActiveDestroys);
        log.info("Destroys loaded: {}", allActiveDestroys.size());
        allActiveDestroys.forEach(build -> heroDestroys.put(build.getHero(), build));
        allActiveDestroys.forEach(build -> build.getHero().setArea(destroyingArea));
    }

    @Override
    public void update(BuildStartDestroyEvent event) {
        Build build = event.build;
        Hero destroyer = event.destroyer;
        build.calcStartDestroy(destroyer);
        buildService.startDestroy(build);

        acquire();
        heroDestroys.put(build.getHero(), build);
        destroys.add(build);
        release();

        BuildStartDestroyingEvent newEvent = new BuildStartDestroyingEvent();
        newEvent.build = build;
        eventManager.newEvent(newEvent);
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 1000)
    public void checkDestroys() {
        while (somethingDestroyed()) {
            Hero hero = nextDestroyedBuild.getHero();
            heroDestroys.remove(hero);

            BuildFinishDestroyEvent event = new BuildFinishDestroyEvent();
            event.destroyedBuild = nextDestroyedBuild;
            eventManager.newEvent(event);
        }
    }

    private boolean somethingDestroyed() {
        if (destroys.isEmpty()) {
            return false;
        }
        nextDestroyedBuild = destroys.first();
        if (nextDestroyedBuild == null || !nextDestroyedBuild.destroyIsFinished()) {
            return false;
        }

        destroys.remove(nextDestroyedBuild);
        return true;
    }

    public Build getByHero(Hero hero) {
        return heroDestroys.get(hero);
    }

    private void acquire() {
        try {
            s.acquire();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void release() {
        s.release();
    }

}
