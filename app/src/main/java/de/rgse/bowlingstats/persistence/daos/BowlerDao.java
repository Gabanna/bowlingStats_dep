package de.rgse.bowlingstats.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.BowlerStatistic;

@Dao
public interface BowlerDao {

    @Query("SELECT * FROM Bowler order by name")
    List<Bowler> getBowlers();

    @Query("SELECT * FROM Bowler where id = :id limit 1")
    Bowler getBowlerById(String id);

    @Query("SELECT * FROM Bowler where name = :name limit 1")
    Bowler getBowlerByName(String name);

    @Query("SELECT b.name, avg(value) as average, max(value) as max, dateTime from Bowler b join SeriesEntry e on b.id = e.bowlerId where b.name = :bowlerId group by dateTime")
    List<BowlerStatistic> getStatistics(String bowlerId);

    @Insert
    void insert(Bowler bowler);

    @Delete
    void delete(Bowler bowler);

    @Update
    void update(Bowler bowler);
}
