package io.cutebot.doka2.model.tool;

import io.cutebot.doka2.repository.entity.ItemEntity;

public class StoredTool extends Tool {

    private final String title;

    private final Integer id;

    private final Integer modifier;

    public StoredTool(ItemEntity itemEntity) {
        this.title = itemEntity.getTitle();
        this.id = itemEntity.getItemId();
        this.modifier = itemEntity.getModifier();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getModifier() {
        return modifier;
    }

    @Override
    public boolean isDropable() {
        return true;
    }
}
