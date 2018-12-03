package io.cutebot.doka2;

import io.cutebot.doka2.area.Area;
import io.cutebot.doka2.model.Race;
import io.cutebot.doka2.model.tool.Tool;
import io.cutebot.doka2.model.weapon.Weapon;
import io.cutebot.doka2.repository.entity.HeroEntity;
import io.cutebot.doka2.service.HeroService;
import io.cutebot.doka2.service.LevelCalc;
import io.cutebot.doka2.service.ToolFactory;
import io.cutebot.doka2.service.WeaponFactory;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.doka2.service.event.level.NewLevelEvent;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.tgmodel.TgUser;

public class Hero {
    private final TgUser tgUser;
    private final HeroService heroService;
    private final EventManager eventManager;

    private String heroName;
    private Race heroRace;
    private Area currentArea;

    private Tool tool;
    private Weapon weapon;
    private Integer experience;

    public Hero(HeroEntity entity, TgUser tgUser, HeroService heroService,
            ToolFactory toolFactory, WeaponFactory weaponFactory, Area currentArea, EventManager eventManager) {
        this.tgUser = tgUser;
        this.heroService = heroService;
        this.eventManager = eventManager;
        this.heroName = entity.getHeroName();
        this.heroRace = entity.getHeroRace();
        this.experience = entity.getExperience();

        this.tool = toolFactory.getTool(entity.getToolId());
        this.weapon = weaponFactory.getWeapon(entity.getWeaponId());
        this.currentArea = currentArea;
    }

    public Long getHeroId() {
        return tgUser.id;
    }

    public boolean hasHeroName() {
        return heroName != null;
    }

    public boolean hasRace() {
        return heroRace != null;
    }

    public void setArea(Area area) {
        this.currentArea = area;
    }

    public SendMessage comingArea(Object data) {
        return findSendComing(currentArea.heroIsComing(this, data));
    }

    public SendMessage handleInput(String text) {
        return findSendComing(currentArea.handleInput(this, text));
    }

    private SendMessage findSendComing(SendMessage sendMessage) {
        int counter = 0;
        while (sendMessage.isRedirect() && counter++ < 10) {
            Area newArea = sendMessage.getRedirectArea();
            this.currentArea = newArea;
            sendMessage = newArea.heroIsComing(this, sendMessage.getRedirectData());
        }
        if (sendMessage.isRedirect()) {
            throw new RuntimeException("Too long redirect loop");
        }
        return sendMessage;
    }


    public void setHeroName(String name) {
        this.heroName = name;
        heroService.updateHeroName(tgUser.id, name);
    }

    public void setRace(Race race) {
        this.heroRace = race;
        heroService.updateHeroRace(tgUser.id, race);
    }

    public String getFullHeroName() {
        return this.getRace().shortTitle() + " " + this.heroName;
    }

    public String getHeroName() {
        return this.heroName;
    }

    public Tool getTool() {
        return tool;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getExperience() {
        return experience;
    }

    public void incrementExperience(int inc) {
        int oldLevel = getLevel();
        experience += inc;
        int newLevel = getLevel();
        if (oldLevel < newLevel) {
            NewLevelEvent event = new NewLevelEvent();
            event.hero = this;
            eventManager.newEvent(event);
        }
        heroService.updateExperience(tgUser.id, experience);
    }

    public int getLevel() {
        return LevelCalc.getLevelByExp(experience);
    }

    public int getStrength() {
        return 99 + getLevel() + weapon.getModifier();
    }

    public int getAgility() {
        return 99 + getLevel() + tool.getModifier();
    }

    public Race getRace() {
        return heroRace;
    }

    public void setWeapon(Weapon weapon) {
        heroService.updateWeapon(tgUser.id, weapon.getId());
        this.weapon = weapon;
    }

    public void setTool(Tool tool) {
        heroService.updateTool(tgUser.id, tool.getId());
        this.tool = tool;
    }
}
