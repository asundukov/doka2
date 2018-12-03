package io.cutebot.doka2.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ItemHelper {

    private static final Logger log = LoggerFactory.getLogger(ItemHelper.class);

    public static int randItemLevel(int heroLevel) {
        int maxItemLevel = heroLevel;
        for (int i = maxItemLevel; i > 0; i--) {
            BigDecimal baseFindChance = BigDecimal.valueOf(0.1).pow(i).multiply(BigDecimal.valueOf(10));
            log.info("Base level chance: {}", baseFindChance);
            BigDecimal heroFindChance = baseFindChance.multiply(BigDecimal.valueOf(heroLevel).pow(2));
            log.info("Hero found chance: {}", heroFindChance);
            if (BigDecimal.valueOf(Math.random()).compareTo(heroFindChance) < 0) {
                return i;
            }
        }
        return 1;
    }
}
