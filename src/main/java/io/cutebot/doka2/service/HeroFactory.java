package io.cutebot.doka2.service;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.tgmodel.TgUser;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Service
public class HeroFactory {

    @Inject
    private HeroService heroService;

    private Map<Long, Hero> allHeroes = new HashMap<>();

    public Hero getHero(TgUser tgUser) {
        if (allHeroes.containsKey(tgUser.id)) {
            return allHeroes.get(tgUser.id);
        }

        Hero hero = heroService.saveOrUpdate(tgUser);
        allHeroes.put(tgUser.id, hero);
        return hero;
    }

    public Hero getHeroById(Long heroId) {
        if (allHeroes.containsKey(heroId)) {
            return allHeroes.get(heroId);
        }
        TgUser user = heroService.getTgById(heroId);
        return getHero(user);
    }
}
