package io.cutebot.doka2.model.tool;

import io.cutebot.doka2.model.Item;
import io.cutebot.doka2.model.ItemType;

public abstract class Tool implements Item {
    public ItemType getType() {
        return ItemType.TOOL;
    }
    @Override
    public String getFullTitle() {
        return getTitle() + " (+" + getModifier() + ")";
    }
}
