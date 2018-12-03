package io.cutebot.doka2.service.build;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.area.BuildingCompleteArea;
import io.cutebot.doka2.model.Action;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.model.BuildSizeType;
import io.cutebot.doka2.service.BuildService;
import io.cutebot.doka2.service.InitiateHandler;
import io.cutebot.doka2.service.event.EventListener;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.doka2.service.event.building.BuildCompleteEvent;
import io.cutebot.doka2.service.event.building.BuildStartDestroyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Service
public class CompletedBuildsService implements EventListener<BuildCompleteEvent> {

    private static final Logger log = LoggerFactory.getLogger(CompletedBuildsService.class);

    @Inject
    private EventManager eventManager;

    @Inject
    private BuildService buildService;

    @Inject
    private BuildingCompleteArea buildingCompleteArea;

    @Inject
    private InitiateHandler initiateHandler;

    private final MultivaluedMap<Hero, Build> completedBuildsByHero = new MultivaluedHashMap<>();
    private final MultivaluedMap<BuildSizeType, Build> completedBuildsBySizes = new MultivaluedHashMap<>();

    private Semaphore s = new Semaphore(1);

    @PostConstruct
    public void init() {
        asList(BuildSizeType.values()).forEach(item -> completedBuildsBySizes.put(item, new ArrayList<>()));
        eventManager.subscribe(this);

        List<Build> completedBuilds = buildService.getNotDestroyedBuilds();
        completedBuilds.forEach(b -> completedBuildsByHero.putSingle(b.getHero(), b));
        completedBuilds.forEach(b -> completedBuildsBySizes.putSingle(b.getBuildSizeType(), b));
        log.info("Builds loaded: {}", completedBuilds.size());
    }


    @Override
    public void update(BuildCompleteEvent event) {
        Build build = event.completedBuild;
        build.calcComplete();
        buildService.complete(build);

        acquire();
        completedBuildsByHero.add(build.getHero(), build);
        completedBuildsBySizes.add(build.getBuildSizeType(), build);
        release();

        Hero hero = build.getHero();
        hero.setArea(buildingCompleteArea);
        initiateHandler.initiate(hero.comingArea(build));
    }

    public Map<BuildSizeType, Integer> getCompletedCountsBySizes() {
        Map<BuildSizeType, Integer> map = new HashMap<>();
        asList(BuildSizeType.values()).forEach(size -> map.put(size, completedBuildsBySizes.get(size).size()));
        return map;
    }

    public Build findAndDestroyLastCompleted(Hero hero) {
        acquire();
        Build build = getLastCompletedBuildByHero(hero);
        if (build != null) {
            destroy(hero, build);
        }
        release();
        return build;
    }

    public Build findAndDestroy(Hero hero, BuildSizeType sizeType) {
        acquire();
        Build build = getCompletedBuildByHeroAndType(hero, sizeType);
        if (build == null) {
            build = getRandomCompletedBySize(sizeType);
        }
        if (build != null) {
            destroy(hero, build);
        }
        release();
        return build;
    }

    private void destroy(Hero destroyer, Build build) {
        completedBuildsByHero.get(build.getHero()).remove(build);
        completedBuildsBySizes.get(build.getBuildSizeType()).remove(build);

        BuildStartDestroyEvent event = new BuildStartDestroyEvent();
        event.build = build;
        event.destroyer = destroyer;
        eventManager.newEvent(event);
    }

    private Build getRandomCompletedBySize(BuildSizeType sizeType) {
        List<Build> builds = new ArrayList<>(completedBuildsBySizes.get(sizeType));
        if (builds.isEmpty()) {
            return null;
        }
        int index = Long.valueOf(Math.round((builds.size() - 1) * Math.random())).intValue();
        return builds.get(index);
    }

    private Build getCompletedBuildByHeroAndType(Hero hero, BuildSizeType sizeType) {
        return completedBuildsByHero.getOrDefault(hero, emptyList()).stream()
                .filter(build -> build.getBuildSizeType().equals(sizeType))
                .findAny().orElse(null);
    }

    private Build getLastCompletedBuildByHero(Hero hero) {
        List<Build> heroBuilds = new ArrayList<>(completedBuildsByHero.getOrDefault(hero, emptyList()));
        if (heroBuilds.isEmpty()) {
            return null;
        }
        heroBuilds.sort(new Action.ActionFinishTimeComparator());
        Collections.reverse(heroBuilds);
        return heroBuilds.get(0);
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
