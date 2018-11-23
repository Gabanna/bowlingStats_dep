package de.rgse.bowlingstats.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.rgse.bowlingstats.model.Session;

@Dao
public interface SessionDao {

    @Query("SELECT * FROM Session")
    List<Session> getSessions();

    @Insert
    void insert(Session session);

    @Delete
    void delete(Session session);
}
