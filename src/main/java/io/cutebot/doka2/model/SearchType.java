package io.cutebot.doka2.model;

import java.util.HashMap;
import java.util.Map;

public enum  SearchType {
    FAST("На районе", "10 мин", 10, 1, 0.6),
    MEDIUM("В окресностях", "2 ч", 120, 3, 0.8),
    LONG("В ебенях", "7 ч", 420, 6, 0.9);

    private final String title;
    private final String timeTitle;
    private final int delay;
    private final String btnTitle;

    private final int findCount;
    private final double findChance;

    private static final Map<String, SearchType> typeByBtnTitle;
    static {
        typeByBtnTitle = new HashMap<>();
        for (SearchType v : SearchType.values()) {
            typeByBtnTitle.put(v.btnTitle, v);
        }
    }

    public static SearchType getByBtnTitle(String buildBtnTitle) {
        return typeByBtnTitle.get(buildBtnTitle);
    }

    SearchType(String title, String timeTitle, int delay, int findCount, double findChance) {
        this.title = title;
        this.timeTitle = timeTitle;
        this.delay = delay;
        this.btnTitle = title + " - " + timeTitle;
        this.findCount = findCount;
        this.findChance = findChance;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeTitle() {
        return timeTitle;
    }

    public int getDelay() {
        return delay;
    }

    public String getBtnTitle() {
        return btnTitle;
    }

    public int getFindCount() {
        return findCount;
    }

    public double getFindChance() {
        return findChance;
    }

}
