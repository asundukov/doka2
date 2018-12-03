package io.cutebot.doka2.service.search;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.area.SearchCompleteArea;
import io.cutebot.doka2.model.Item;
import io.cutebot.doka2.model.ItemHelper;
import io.cutebot.doka2.model.Search;
import io.cutebot.doka2.model.SearchType;
import io.cutebot.doka2.service.InitiateHandler;
import io.cutebot.doka2.service.SearchService;
import io.cutebot.doka2.service.ToolFactory;
import io.cutebot.doka2.service.WeaponFactory;
import io.cutebot.doka2.service.event.EventListener;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.doka2.service.event.search.SearchCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchCompleteService implements EventListener<SearchCompletedEvent> {

    private static final Logger log = LoggerFactory.getLogger(SearchCompleteService.class);

    @Inject
    private SearchService searchService;

    @Inject
    private EventManager eventManager;

    @Inject
    private WeaponFactory weaponFactory;

    @Inject
    private ToolFactory toolFactory;

    @Inject
    private SearchCompleteArea searchCompleteArea;

    @Inject
    private InitiateHandler initiateHandler;

    @PostConstruct
    public void init() {
        eventManager.subscribe(this);
    }

    @Override
    public void update(SearchCompletedEvent event) {
        Search search = event.search;
        SearchType searchType = search.getSearchType();

        Hero hero = search.getHero();
        int heroLevel = hero.getLevel();

        int findCount = randFindCount(searchType);

        List<Item> foundItems = new ArrayList<>(findCount);
        for (int i = 0; i < findCount; i++) {
            int foundItemLevel = ItemHelper.randItemLevel(heroLevel);

            Item item = generateItem(foundItemLevel);
            foundItems.add(item);
        }

        search.setFoundItems(foundItems);
        searchService.complete(search.getId());

        hero.setArea(searchCompleteArea);
        initiateHandler.initiate(hero.comingArea(search));
    }

    private Item generateItem(int foundItemLevel) {
        if (Math.random() < 0.5) {
            return weaponFactory.generateWeapon(foundItemLevel);
        } else {
            return toolFactory.generateTool(foundItemLevel);
        }
    }

    private int randFindCount(SearchType type) {
        int count = 0;
        log.info("Find count: {}", type.getFindCount());
        for (int i = 0; i < type.getFindCount(); i++) {
            double d = Math.random();
            log.info("Rand check: {}, chance: {}", d, type.getFindChance());
            if (d < type.getFindChance()) {
                count++;
            }
        }

        return count;
    }
}
