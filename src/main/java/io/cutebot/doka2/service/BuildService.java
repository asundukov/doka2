package io.cutebot.doka2.service;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.model.BuildSizeType;
import io.cutebot.doka2.repository.entity.BuildEntity;
import io.cutebot.doka2.repository.repo.BuildRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static io.cutebot.AppConfig.DEFAULT_TIME_UNIT;

@Service
public class BuildService {
    private static final Logger log = LoggerFactory.getLogger(BuildService.class);
    @Inject
    private BuildRepository repository;

    @Inject
    private HeroFactory heroFactory;

    public Build createNewBuild(Hero hero, BuildSizeType sizeType) {
        Calendar finishTime = Calendar.getInstance();
        finishTime.add(DEFAULT_TIME_UNIT, sizeType.getDelay());

        BuildEntity entity = new BuildEntity();
        entity.setBuildSizeType(sizeType);
        entity.setStartTime(Calendar.getInstance());
        entity.setFinishTime(finishTime);
        entity.setCreatorId(hero.getHeroId());

        entity.setBuildActive(true);
        entity.setDestroyActive(false);

        entity = repository.save(entity);

        return new Build(hero, entity, heroFactory);
    }

    public void complete(Build build) {
        BuildEntity entity = repository.findById(build.getId())
                .orElseThrow(() -> new RuntimeException("Cannot find build " + build.getId()));
        entity.setSize(build.getSize());
        entity.setGainedExp(build.getGainedExp());
        entity.setBuildActive(false);
        repository.save(entity);
    }

    public void startDestroy(Build build) {
        BuildEntity entity = repository.findById(build.getId())
                .orElseThrow(() -> new RuntimeException("Cannot find build " + build.getId()));
        entity.setStartDestroyTime(build.getStartDestroyTime());
        entity.setFinishDestroyTime(build.getFinishDestroyTime());
        entity.setDestroyerId(build.getDestroyer().getHeroId());
        entity.setDestroyActive(true);
        repository.save(entity);
    }

    public void completeDestroy(Build build) {
        BuildEntity entity = repository.findById(build.getId())
                .orElseThrow(() -> new RuntimeException("Cannot find build " + build.getId()));
        entity.setGainedDestroyExp(build.getGainedDestroyExp());
        entity.setDestroyActive(false);
        repository.save(entity);
    }

    public List<Build> getActiveBuilds() {
        return repository.findAllByBuildActiveIsTrue().stream()
                .map(b -> new Build(heroFactory.getHeroById(b.getCreatorId()), b, heroFactory))
                .collect(Collectors.toList());
    }

    public List<Build> getActiveDestroys() {
        return repository.findAllByDestroyActiveIsTrue().stream()
                .map(b -> new Build(heroFactory.getHeroById(b.getCreatorId()), b, heroFactory))
                .collect(Collectors.toList());
    }

    public List<Build> getNotDestroyedBuilds() {
        return repository.findAllByBuildActiveIsFalseAndGainedDestroyExpIsNull().stream()
                .map(b -> new Build(heroFactory.getHeroById(b.getCreatorId()), b, heroFactory))
                .collect(Collectors.toList());
    }
}
