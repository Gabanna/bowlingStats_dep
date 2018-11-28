package de.rgse.bowlingstats.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

@Entity(
        foreignKeys = @ForeignKey(entity = Bowler.class, parentColumns = "id", childColumns = "bowlerId"),
        indices = {@Index("bowlerId"), @Index("dateTime")}
)
public class SeriesEntry implements Comparable<SeriesEntry> {

    @PrimaryKey
    @NonNull
    private String id;
    private String bowlerId;
    private Date dateTime;
    private int value;

    public SeriesEntry(String bowlerId, int value, Date dateTime) {
        this.id = UUID.randomUUID().toString();
        this.bowlerId = bowlerId;
        this.dateTime = dateTime;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int compareTo(SeriesEntry o) {
        return dateTime.compareTo(o.dateTime);
    }
}
