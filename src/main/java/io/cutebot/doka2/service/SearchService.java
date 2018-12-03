package io.cutebot.doka2.service;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Search;
import io.cutebot.doka2.model.SearchType;
import io.cutebot.doka2.repository.entity.SearchEntity;
import io.cutebot.doka2.repository.repo.SearchRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static io.cutebot.AppConfig.DEFAULT_TIME_UNIT;

@Service
public class SearchService {

    @Inject
    private SearchRepository repository;

    @Inject
    private HeroFactory heroFactory;

    public Search createNewSearch(Hero hero, SearchType searchType) {
        Calendar finishTime = Calendar.getInstance();
        finishTime.add(DEFAULT_TIME_UNIT, searchType.getDelay());

        SearchEntity entity = new SearchEntity();
        entity.setHeroId(hero.getHeroId());
        entity.setSearchType(searchType);
        entity.setStartTime(Calendar.getInstance());
        entity.setFinishTime(finishTime);
        entity.setActive(true);
        entity = repository.save(entity);

        return new Search(hero, entity);
    }

    public void complete(int searchId) {
        SearchEntity entity = repository.findById(searchId)
                .orElseThrow(() -> new RuntimeException("Unknown search " + searchId));
        entity.setActive(false);
        repository.save(entity);
    }

    public List<Search> findActiveSearches() {
        return repository.findAllByIsActiveIsTrue().stream()
                .map(searchEntity -> new Search(heroFactory.getHeroById(searchEntity.getHeroId()), searchEntity))
                .collect(Collectors.toList());
    }
}
