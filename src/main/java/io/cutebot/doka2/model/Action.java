package io.cutebot.doka2.model;

import io.cutebot.doka2.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Comparator;

public abstract class Action {

    private static final Logger log = LoggerFactory.getLogger(Action.class);

    protected final Hero hero;
    protected int id;
    private Calendar finishTime;

    public Action(Hero hero, Calendar finishTime, Integer id) {
        this.hero = hero;
        this.finishTime = finishTime;
        this.id = id;
    }

    public boolean isFinished() {
        return Calendar.getInstance().after(finishTime);
    }

    public Hero getHero() {
        return hero;
    }

    public String getTimeRemaining() {
        return getTimeRemaining(finishTime.getTimeInMillis());
    }

    public static String getTimeRemaining(long finTime) {
        long currTime = Calendar.getInstance().getTimeInMillis();

        long diffSeconds = (finTime - currTime) / 1000;
        if (diffSeconds <= 0) {
            return "уже";
        }
        if (diffSeconds < 120) {
            return diffSeconds + " сек";
        }
        long diffMinutes = diffSeconds / 60;
        if (diffMinutes < 120) {
            return diffMinutes + " мин";
        }
        return (diffMinutes / 60) + " ч";
    }

    public Integer getId() {
        return id;
    }

    public static class ActionFinishTimeComparator implements Comparator<Action> {
        @Override
        public int compare(Action o1, Action o2) {
            if (o1.finishTime.before(o2.finishTime)) {
                return -1;
            } else if (o1.finishTime.after(o2.finishTime)){
                return 1;
            } else {
                return o1.id - o2.id;
            }
        }
    }
}
