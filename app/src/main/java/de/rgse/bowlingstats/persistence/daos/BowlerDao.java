package de.rgse.bowlingstats.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;

@Dao
public interface BowlerDao {

    @Query("SELECT * FROM Bowler order by name")
    List<Bowler> getBowlers();

    @Query("SELECT * FROM Bowler where id = :id limit 1")
    Bowler getBowlerById(String id);

    @Insert
    void insert(Bowler bowler);

    @Delete
    void delete(Bowler bowler);

    @Update
    void update(Bowler bowler);
}
