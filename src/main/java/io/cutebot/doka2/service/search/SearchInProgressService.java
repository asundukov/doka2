package io.cutebot.doka2.service.search;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.area.SearchingArea;
import io.cutebot.doka2.model.Action;
import io.cutebot.doka2.model.Search;
import io.cutebot.doka2.model.SearchType;
import io.cutebot.doka2.service.SearchService;
import io.cutebot.doka2.service.event.EventManager;
import io.cutebot.doka2.service.event.search.SearchCompletedEvent;
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
public class SearchInProgressService {
    private static final Logger log = LoggerFactory.getLogger(SearchInProgressService.class);
    @Inject
    private SearchService searchService;

    @Inject
    private EventManager eventManager;

    @Inject
    private SearchingArea searchingArea;

    private Map<Hero, Search> heroSearches = new HashMap<>();
    private TreeSet<Search> searches = new TreeSet<>(new Action.ActionFinishTimeComparator());

    private Search nextCompletedSearch;

    @PostConstruct
    public void init() {
        List<Search> allActiveSearches = searchService.findActiveSearches();
        searches.addAll(allActiveSearches);
        log.info("Active searches loaded: {}", allActiveSearches.size());

        allActiveSearches.forEach(search -> heroSearches.put(search.getHero(), search));
        allActiveSearches.forEach(build -> build.getHero().setArea(searchingArea));
    }

    public Search createNewSearch(Hero hero, SearchType searchType) {
        Search search = searchService.createNewSearch(hero, searchType);
        searches.add(search);
        heroSearches.put(search.getHero(), search);
        return search;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 1000)
    public void checkSearches() {
        while (somethingCompleted()) {
            Hero hero = nextCompletedSearch.getHero();
            heroSearches.remove(hero);

            SearchCompletedEvent event = new SearchCompletedEvent();
            event.search = nextCompletedSearch;
            eventManager.newEvent(event);
        }
    }

    private boolean somethingCompleted() {
        if (searches.isEmpty()) {
            return false;
        }
        nextCompletedSearch = searches.first();
        if (nextCompletedSearch == null || !nextCompletedSearch.isFinished()) {
            return false;
        }

        searches.remove(nextCompletedSearch);
        return true;
    }

    public Search getByHero(Hero hero) {
        return heroSearches.get(hero);
    }
}
