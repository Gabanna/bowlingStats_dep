package de.rgse.bowlingstats.tasks;

public interface Callback<T> {

    void call(T arguments);
}
