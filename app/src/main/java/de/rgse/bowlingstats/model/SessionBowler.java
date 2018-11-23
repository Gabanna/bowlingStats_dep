package de.rgse.bowlingstats.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(
        primaryKeys = {"sessionId", "bowlerId"},
        indices = @Index({"sessionId", "bowlerId"})
        )
public class SessionBowler {

    @NonNull
    private String sessionId;
    @NonNull
    private String bowlerId;

    public SessionBowler(String sessionId, String bowlerId) {
        this.sessionId = sessionId;
        this.bowlerId = bowlerId;
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
