package de.rgse.bowlingstats.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;
import java.util.UUID;

@Entity(
        indices = @Index(value = "name", unique = true)
)
public class Bowler {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    public Bowler(){}

    @Ignore
    public Bowler(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bowler bowler = (Bowler) o;
        return id == bowler.id &&
                Objects.equals(name, bowler.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

