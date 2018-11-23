package de.rgse.bowlingstats.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import javax.annotation.PropertyKey;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = Bowler.class, parentColumns = "id", childColumns = "bowlerId"),
                @ForeignKey(entity = Session.class, parentColumns = "id", childColumns = "sessionId"),
        },
        indices = {
                @Index("sessionId"),
                @Index("bowlerId")
        }
)
public class Series {

    @PrimaryKey
    @NonNull
    private String id;
    private String sessionId;
    private String bowlerId;

    public Series(String sessionId, String bowlerId) {
        this.sessionId = sessionId;
        this.bowlerId = bowlerId;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }
}
