package de.rgse.bowlingstats.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;

@Dao
public interface BowlerDao {

    @Query("SELECT * FROM Bowler")
    List<Bowler> getBowlers();

    @Insert
    void insert(Bowler bowler);

    @Delete
    void delete(Bowler bowler);
}
