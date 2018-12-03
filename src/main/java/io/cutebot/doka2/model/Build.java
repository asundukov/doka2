package io.cutebot.doka2.model;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.repository.entity.BuildEntity;
import io.cutebot.doka2.service.HeroFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;

import static io.cutebot.AppConfig.DEFAULT_TIME_UNIT;
import static java.math.RoundingMode.HALF_UP;

public class Build extends Action {

    private static final Logger log = LoggerFactory.getLogger(Build.class);

    private final BuildSizeType buildSizeType;

    private Integer gainedExp;
    private Integer size;

    private Calendar finishDestroyTime;
    private Calendar startDestroyTime;
    private Hero destroyer;
    private Integer gainedDestroyExp;

    public Build(Hero hero, BuildEntity buildEntity, HeroFactory heroFactory) {
        super(hero, buildEntity.getFinishTime(), buildEntity.getBuildId());
        this.buildSizeType = buildEntity.getBuildSizeType();
        this.gainedExp = buildEntity.getGainedExp();
        this.size = buildEntity.getSize();
        this.startDestroyTime = buildEntity.getStartDestroyTime();
        this.finishDestroyTime = buildEntity.getFinishDestroyTime();
        if (buildEntity.getDestroyerId() != null) {
            this.destroyer = heroFactory.getHeroById(buildEntity.getDestroyerId());
        }
        this.gainedDestroyExp = buildEntity.getGainedDestroyExp();
    }

    public void calcComplete() {
        if (size != null) {
            log.warn("Trying to complete completed build id:{}", id);
            return;
        }
        int baseSize = buildSizeType.getBaseSize();
        BigDecimal rate = BigDecimal.valueOf(hero.getAgility() - 100).divide(BigDecimal.valueOf(100), 8, HALF_UP);
        int agilitySize = BigDecimal.valueOf(baseSize).multiply(rate).intValue();
        size = baseSize + agilitySize;
        gainedExp = size;
        hero.incrementExperience(gainedExp);
    }

    public void calcStartDestroy(Hero destroyer) {
        if(startDestroyTime != null) {
            log.warn("Trying to start destroy build already destroying. Build {}, Destroyer {}", this, destroyer);
            return;
        }
        this.startDestroyTime = Calendar.getInstance();
        this.destroyer = destroyer;
        Calendar finishTime = Calendar.getInstance();
        finishTime.add(DEFAULT_TIME_UNIT, buildSizeType.getDelay());
        this.finishDestroyTime = finishTime;
    }

    public boolean destroyIsFinished() {
        return Calendar.getInstance().after(finishDestroyTime);
    }

    public BuildSizeType getBuildSizeType() {
        return buildSizeType;
    }

    public int getSize() {
        return size;
    }

    public Integer getGainedExp() {
        return gainedExp;
    }

    public Calendar getFinishDestroyTime() {
        return finishDestroyTime;
    }

    public Calendar getStartDestroyTime() {
        return startDestroyTime;
    }

    public Hero getDestroyer() {
        return destroyer;
    }

    public void calcDestroy() {
        if (gainedDestroyExp != null) {
            log.warn("Wrong call calc Destroy: gainedDestroyExp already calculated.");
            return;
        }
        BigDecimal rate = BigDecimal.valueOf(hero.getStrength() - 100).divide(BigDecimal.valueOf(100), 8, HALF_UP);
        int strengthSize = BigDecimal.valueOf(size).multiply(rate).intValue();
        gainedDestroyExp = size + strengthSize;
        destroyer.incrementExperience(gainedDestroyExp);
    }

    public String getDestroyTimeRemaining() {
        return getTimeRemaining(finishDestroyTime.getTimeInMillis());
    }

    public Integer getGainedDestroyExp() {
        return gainedDestroyExp;
    }

    public static class DestroyFinishTimeComparator implements Comparator<Build> {
        @Override
        public int compare(Build o1, Build o2) {
            if (o1.finishDestroyTime.before(o2.finishDestroyTime)) {
                return -1;
            } else if (o1.finishDestroyTime.after(o2.finishDestroyTime)){
                return 1;
            } else {
                return o1.id - o2.id;
            }
        }
    }
}
