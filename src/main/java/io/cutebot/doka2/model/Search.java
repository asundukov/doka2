package io.cutebot.doka2.model;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.repository.entity.SearchEntity;

import java.util.List;

public class Search extends Action {
    private final Integer id;
    private final SearchType searchType;

    private List<Item> foundItems;


    public Search(Hero hero, SearchEntity entity) {
        super(hero, entity.getFinishTime(), entity.getSearchingId());
        this.id = entity.getSearchingId();
        this.searchType = entity.getSearchType();
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public List<Item> getFoundItems() {
        return foundItems;
    }

    public void setFoundItems(List<Item> searchedItems) {
        this.foundItems = searchedItems;
    }
}
