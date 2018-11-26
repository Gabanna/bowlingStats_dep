package de.rgse.bowlingstats.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import de.rgse.bowlingstats.model.Series;
import de.rgse.bowlingstats.model.SeriesEntry;

@Dao
public interface SeriesDao {

    @Query("SELECT * FROM Series where sessionId = :sessionId")
    List<Series> getSeriesBySession(String sessionId);

    @Query("SELECT * FROM SeriesEntry where seriesId = :seriesId")
    List<SeriesEntry> getSeriesEntryBySeries(String seriesId);
}
