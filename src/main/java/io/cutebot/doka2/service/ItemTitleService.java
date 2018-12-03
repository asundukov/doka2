package io.cutebot.doka2.service;

import io.cutebot.doka2.model.ItemType;
import io.cutebot.doka2.model.TitleType;
import io.cutebot.doka2.repository.entity.ItemTitleEntity;
import io.cutebot.doka2.repository.repo.ItemTitleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemTitleService {

    private static final Logger log = LoggerFactory.getLogger(ItemTitleService.class);

    private final List<ItemTitleEntity> mainTitles = new ArrayList<>();

    private final List<ItemTitleEntity> firstTitles = new ArrayList<>();

    private final List<ItemTitleEntity> lastTitles = new ArrayList<>();

    @Inject
    public ItemTitleService(ItemTitleRepository repository) {
        List<ItemTitleEntity> all = repository.findAll();
        for (ItemTitleEntity e : all) {
            if (e.getTitleType().equals(TitleType.MAIN)) {
                mainTitles.add(e);
            }
            if (e.getTitleType().equals(TitleType.FIRST)) {
                firstTitles.add(e);
            }
            if (e.getTitleType().equals(TitleType.LAST)) {
                lastTitles.add(e);
            }
        }
        log.info("Loaded titles: {}", all.size());
    }

    public String generateTitle(ItemType itemType, Integer itemLevel) {
        List<ItemTitleEntity> allMain = mainTitles.stream()
            .filter(t -> t.getItemType().equals(itemType))
            .filter(t -> t.getMinLevel() <= itemLevel)
            .filter(t -> t.getMaxLevel() >= itemLevel)
            .collect(Collectors.toList());

        if (allMain.isEmpty()) {
            return getDefaultItemName(itemType, itemLevel);
        }

        ItemTitleEntity selectedMain = allMain.get((int) Math.round(Math.floor(Math.random() * allMain.size())));
        String fullTitle = selectedMain.getTitle();

        List<ItemTitleEntity> allFirst = getFilteredEntities(itemType, itemLevel, selectedMain, firstTitles);
        if (!allFirst.isEmpty()) {
            ItemTitleEntity selectedFirst = allFirst.get((int) Math.round(Math.floor(Math.random() * allMain.size())));
            if (!selectedFirst.getTitle().isEmpty()) {
                fullTitle = selectedFirst.getTitle() + " " + fullTitle;
            }
        }

        List<ItemTitleEntity> allLast = getFilteredEntities(itemType, itemLevel, selectedMain, lastTitles);
        if (!allLast.isEmpty()) {
            ItemTitleEntity selectedLast = allLast.get((int) Math.round(Math.floor(Math.random() * allMain.size())));
            if (!selectedLast.getTitle().isEmpty()) {
                fullTitle = fullTitle + " " + selectedLast.getTitle();
            }
        }
        return fullTitle;
    }

    private List<ItemTitleEntity> getFilteredEntities(ItemType itemType, Integer itemLevel,
            ItemTitleEntity selectedMain, List<ItemTitleEntity> firstTitles) {
        return firstTitles.stream()
                .filter(t -> t.getItemType().equals(itemType))
                .filter(t -> t.getSex().equals(selectedMain.getSex()))
                .filter(t -> t.getMinLevel() <= itemLevel)
                .filter(t -> t.getMaxLevel() >= itemLevel)
                .collect(Collectors.toList());
    }

    private String getDefaultItemName(ItemType itemType, Integer itemLevel) {
        log.warn("Cannot create title for type {} and level {}", itemType, itemLevel);
        if (itemType.equals(ItemType.TOOL)) {
            return "Странный инструмент";
        } else {
            return "Странная хреновина";
        }
    }

}
