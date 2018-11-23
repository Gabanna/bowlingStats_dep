package de.rgse.bowlingstats.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Session {

    @PrimaryKey
    @NonNull
    private String id;

    private Date dateTime;

    public Session() {
        this.id = UUID.randomUUID().toString();
        this.dateTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id == session.id &&
                Objects.equals(dateTime, session.dateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateTime);
    }
}
