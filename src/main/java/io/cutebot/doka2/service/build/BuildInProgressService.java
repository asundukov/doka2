package io.cutebot.doka2.service.build;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.area.BuildingArea;
import io.cutebot.doka2.model.Action;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.model.BuildSizeType;
import io.cutebot.doka2.service.BuildService;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.doka2.service.event.building.BuildCompleteEvent;
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

@Service
public class BuildInProgressService {

    private static final Logger log = LoggerFactory.getLogger(BuildInProgressService.class);
    @Inject
    private BuildService buildService;

    @Inject
    private EventManager eventManager;

    @Inject
    private BuildingArea buildingArea;

    private Map<Hero, Build> heroBuilds = new HashMap<>();
    private TreeSet<Build> builds = new TreeSet<>(new Action.ActionFinishTimeComparator());

    private Build nextCompletedBuild;

    @PostConstruct
    public void init() {
        List<Build> allActiveBuilds = buildService.getActiveBuilds();
        builds.addAll(allActiveBuilds);
        log.info("Builds loaded: {}", allActiveBuilds.size());
        allActiveBuilds.forEach(build -> heroBuilds.put(build.getHero(), build));
        allActiveBuilds.forEach(build -> build.getHero().setArea(buildingArea));
    }

    public Build createNewBuild(Hero hero, BuildSizeType sizeType) {
        Build build = buildService.createNewBuild(hero, sizeType);
        builds.add(build);
        heroBuilds.put(build.getHero(), build);
        return build;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 1000)
    public void checkBuildings() {
        while (somethingCompleted()) {
            Hero hero = nextCompletedBuild.getHero();
            heroBuilds.remove(hero);

            BuildCompleteEvent event = new BuildCompleteEvent();
            event.completedBuild = nextCompletedBuild;
            eventManager.newEvent(event);
        }
    }

    private boolean somethingCompleted() {
        if (builds.isEmpty()) {
            return false;
        }
        nextCompletedBuild = builds.first();
        if (nextCompletedBuild == null || !nextCompletedBuild.isFinished()) {
            return false;
        }

        builds.remove(nextCompletedBuild);
        return true;
    }

    public Build getByHero(Hero hero) {
        return heroBuilds.get(hero);
    }
}
