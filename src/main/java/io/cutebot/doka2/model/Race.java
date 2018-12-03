package io.cutebot.doka2.model;

import java.util.HashMap;
import java.util.Map;

public enum Race {
    STUDENT("👦🏻", "Школьник"),
    ZOMBIE("💀", "Зомби"),
    ELF("\uD83D\uDE3A", "Эльф");

    private final String shortTitle;
    private final String title;
    private final String fullTitle;

    private static final Map<String, Race> raceByFullTitle;
    static {
        raceByFullTitle = new HashMap<>();
        for (Race v : Race.values()) {
            raceByFullTitle.put(v.fullTitle, v);
        }
    }

    public static Race getByFullTitle(String buildBtnTitle) {
        return raceByFullTitle.get(buildBtnTitle);
    }

    Race(String shortTitle, String title) {
        this.shortTitle = shortTitle;
        this.title = title;
        this.fullTitle = shortTitle + " " + title;
    }

    public String shortTitle() {
        return shortTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getFullTitle() {
        return fullTitle;
    }
}
