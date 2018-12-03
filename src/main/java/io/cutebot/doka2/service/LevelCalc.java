package io.cutebot.doka2.service;

import java.util.ArrayList;
import java.util.List;

public class LevelCalc {

    private static List<Integer> expSteps = new ArrayList<>(1000);
    static {
        for (int i = 1; i <= 1000; i++) {
            expSteps.add(25 * (i * i - i));
        }
    }

    public static int getLevelByExp(int exp) {
        int nextStep = 100;
        int lvl = 1;
        while (nextStep <= exp) {
            lvl++;
            nextStep = nextStep + 50 * lvl;
        }
        return lvl;
    }
}
