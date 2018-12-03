package io.cutebot.doka2.model;

public interface Item {
    Integer getId();
    String getTitle();
    String getFullTitle();
    Integer getModifier();
    ItemType getType();

    boolean isDropable();
}
