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

}
