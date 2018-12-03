package io.cutebot.doka2.model;

import java.util.HashMap;
import java.util.Map;

public enum BuildSizeType {
    VERY_SMALL("Крошечную", "5 мин", "Крошечных", 5, 20),
    SMALL("Маленькую", "15 мин", "Маленьких", 15, 30),
    MEDIUM("Среднюю", "1ч", "Средних", 60, 100),
    BIG("Большую", "8ч", "Больших", 480, 500);

    private final String btnTitle;
    private final String countTitle;
    private final String timeStr;
    private final int delay;
    private final int baseSize;

    private static final Map<String, BuildSizeType> typeByTitle;
    static {
        typeByTitle = new HashMap<>();
        for (BuildSizeType v : BuildSizeType.values()) {
            typeByTitle.put(v.btnTitle, v);
        }
    }

    public static BuildSizeType getByBtnTitle(String buildBtnTitle) {
        return typeByTitle.get(buildBtnTitle);
    }

    BuildSizeType(String btnTitle, String timeStr, String countTitle, int delay, int baseSize) {
        this.btnTitle = btnTitle + " - " + timeStr;
        this.countTitle = countTitle;
        this.timeStr = timeStr;
        this.delay = delay;
        this.baseSize = baseSize;
    }

    public String getBtn() {
        return btnTitle;
    }

    public String getCountTitle() {
        return countTitle;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public int getDelay() {
        return delay;
    }

    public int getBaseSize() {
        return baseSize;
    }
}
