package fr.istic.starv1KM.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * @Author YEO KEVIN | MOUSSA BAMBA
 */
@Dao
public interface CalendarDao {

    @Query("Select * from " + StarContract.Calendar.CONTENT_PATH)
    List<CalendarEntity> getCalendarList();

    @Query("Select * from " + StarContract.Calendar.CONTENT_PATH)
    Cursor getCalendarListCursor();

    @Insert
    void insertAll(ArrayList<CalendarEntity> calendarEntities);

    @Query("DELETE FROM " + StarContract.Calendar.CONTENT_PATH)
    void deleteAll();
}
