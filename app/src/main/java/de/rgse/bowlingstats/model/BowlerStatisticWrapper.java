package de.rgse.bowlingstats.model;

import java.util.Collections;
import java.util.List;

public class BowlerStatisticWrapper {

    private List<BowlerStatistic> statistic;

    public BowlerStatisticWrapper(List<BowlerStatistic> statistic) {
        this.statistic = statistic;
    }

    public List<BowlerStatistic> getStatistic() {
        return Collections.unmodifiableList(statistic);
    }

    public float calculateOverallAverage() {
        int count = 0;
        int value = 0;

        for(BowlerStatistic stat : statistic) {
            count += stat.getCount();
            value += (stat.getAverage() * stat.getCount());
        }

        return count == 0 ?  0 : value / count;
    }
}
