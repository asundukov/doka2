package io.cutebot.doka2.model.weapon;

import io.cutebot.doka2.model.Item;
import io.cutebot.doka2.model.ItemType;

public abstract class Weapon implements Item {
    @Override
    public String getFullTitle() {
        return getTitle() + " (+" + getModifier() + ")";
    }
    public ItemType getType() {
        return ItemType.WEAPON;
    }
}
