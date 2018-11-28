package de.rgse.bowlingstats.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.SeriesEntry;

@Dao
public interface SeriesDao {

    @Query("SELECT distinct dateTime FROM SeriesEntry")
    List<Date> getEntriyDates();

    @Query("SELECT * FROM SeriesEntry where dateTime between :from and :to")
    List<SeriesEntry> getEntriesByDate(Date from, Date to);

    @Insert
    void insert(SeriesEntry entry);

}
