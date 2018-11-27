package de.rgse.bowlingstats.persistence;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.persistence.daos.BowlerDao;
import de.rgse.bowlingstats.persistence.daos.SeriesDao;

@android.arch.persistence.room.Database(entities = {Bowler.class, SeriesEntry.class}, version = 4, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract BowlerDao bowlerDao();
    public abstract SeriesDao seriesDao();

    public static Database getInstance(Context context) {
        if(instance == null) {
            instance = Room
                    .databaseBuilder(context, Database.class, "bowling-stats-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
