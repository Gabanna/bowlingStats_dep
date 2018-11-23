package de.rgse.bowlingstats.persistence;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Converters {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance();

    @TypeConverter
    public static String dateTimeToTimestamp(Date date) {
        return date == null ? null : DATE_FORMAT.format(date);
    }

    @TypeConverter
    public static Date timestampToDate(String timestamp) {
        Date result = null;
        try {
            return timestamp == null ? null : DATE_FORMAT.parse(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
