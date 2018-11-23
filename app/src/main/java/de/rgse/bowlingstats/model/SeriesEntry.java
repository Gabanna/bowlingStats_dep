package de.rgse.bowlingstats.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(
        foreignKeys = @ForeignKey(entity = Series.class, parentColumns = "id", childColumns = "seriesId"),
        indices = @Index("seriesId")
)
public class SeriesEntry {

    @PrimaryKey
    @NonNull
    private String id;
    private String seriesId;
    private int value;

    public SeriesEntry(String seriesId, int value) {
        this.id = UUID.randomUUID().toString();
        this.seriesId = seriesId;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
