package com.chancellor.degreemap.utilities;

import androidx.room.TypeConverter;

import java.sql.Date;

public class DateTypeConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    public static Date toDate(String strDate) {
        return Date.valueOf(strDate);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
