package io.cutebot.doka2.service;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.area.NoArea;
import io.cutebot.doka2.model.Race;
import io.cutebot.doka2.repository.entity.HeroEntity;
import io.cutebot.doka2.repository.repo.HeroRepository;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.telegram.tgmodel.TgUser;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Calendar;

@Service
public class HeroService {
    @Inject
    private HeroRepository repository;

    @Inject
    private ToolFactory toolFactory;

    @Inject
    private WeaponFactory weaponFactory;

    @Inject
    private NoArea noArea;

    @Inject
    private EventManager eventManager;

    public Hero saveOrUpdate(TgUser tgUser) {
        HeroEntity heroEntity = repository.findById(tgUser.id)
                .orElseGet(() -> create(tgUser));
        heroEntity.setFirstName(tgUser.firstName);
        heroEntity.setLastName(tgUser.lastName);
        heroEntity.setUserName(tgUser.userName);
        heroEntity.setLanguageCode(tgUser.languageCode);
        heroEntity.setLastRequest(Calendar.getInstance());
        repository.save(heroEntity);
        return new Hero(heroEntity, tgUser, this, toolFactory, weaponFactory, noArea, eventManager);
    }

    private HeroEntity create(TgUser tgUser) {
        HeroEntity entity = new HeroEntity();
        entity.setHeroId(tgUser.id);
        entity.setExperience(0);
        return entity;
    }

    public void updateHeroName(Long heroId, String name) {
        HeroEntity heroEntity = getExistedHeroEntityById(heroId);
        heroEntity.setHeroName(name);
        repository.save(heroEntity);
    }

    public void updateHeroRace(Long heroId, Race race) {
        HeroEntity heroEntity = getExistedHeroEntityById(heroId);
        heroEntity.setHeroRace(race);
        repository.save(heroEntity);
    }

    private HeroEntity getExistedHeroEntityById(Long heroId) {
        return repository.findById(heroId)
                .orElseThrow(() -> new RuntimeException("Cant find by hero id " + heroId));
    }

    public void updateExperience(Long id, Integer experience) {
        HeroEntity heroEntity = getExistedHeroEntityById(id);
        heroEntity.setExperience(experience);
        repository.save(heroEntity);
    }

    public TgUser getTgById(Long heroId) {
        TgUser user = new TgUser();
        HeroEntity entity = repository.findById(heroId)
                .orElseThrow(() -> new RuntimeException("Cant find hero by id " + heroId));
        user.id = entity.getHeroId();
        user.firstName = entity.getFirstName();
        user.lastName = entity.getLastName();
        user.languageCode = entity.getLanguageCode();
        user.userName = entity.getUserName();
        return user;
    }

    public void updateWeapon(Long heroId, Integer weaponId) {
        HeroEntity entity = getExistedHeroEntityById(heroId);
        entity.setWeaponId(weaponId);
        repository.save(entity);
    }

    public void updateTool(Long heroId, Integer toolId) {
        HeroEntity entity = getExistedHeroEntityById(heroId);
        entity.setToolId(toolId);
        repository.save(entity);
    }
}
