package io.cutebot.doka2.service;

import io.cutebot.doka2.model.ItemType;
import io.cutebot.doka2.repository.entity.ItemEntity;
import io.cutebot.doka2.repository.repo.ItemRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ItemService {

    @Inject
    private ItemRepository repository;

    public ItemEntity getExistedItemById(Integer toolId) {
        return repository.findById(toolId)
                .orElseThrow(() -> new RuntimeException("Can find item " + toolId));
    }

    public ItemEntity create(String title, Integer modifier, ItemType itemType) {
        ItemEntity entity = new ItemEntity();
        entity.setTitle(title);
        entity.setModifier(modifier);
        entity.setItemType(itemType);
        repository.save(entity);
        return entity;
    }
}
