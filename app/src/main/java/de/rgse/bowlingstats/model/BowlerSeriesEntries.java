package de.rgse.bowlingstats.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BowlerSeriesEntries {

    private Bowler bowler;
    private List<SeriesEntry> entries;

    public BowlerSeriesEntries(Bowler bowler) {
        this.bowler = bowler;
        entries = new ArrayList<>();
    }

    public List<SeriesEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void addSeriesEntry(SeriesEntry seriesEntry) {
        entries.add(seriesEntry);
    }

    public Bowler getBowler() {
        return bowler;
    }
}
