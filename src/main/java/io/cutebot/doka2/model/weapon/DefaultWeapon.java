package io.cutebot.doka2.model.weapon;

public class DefaultWeapon extends Weapon {
    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Правая рука";
    }

    @Override
    public String getFullTitle() {
        return getTitle();
    }

    @Override
    public Integer getModifier() {
        return 0;
    }

    @Override
    public boolean isDropable() {
        return false;
    }
}
