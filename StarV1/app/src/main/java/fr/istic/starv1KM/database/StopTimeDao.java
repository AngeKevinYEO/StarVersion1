package fr.istic.starv1KM.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * @Author YEO KEVIN | BAMBA MOUSSA
 */
@Dao
public interface StopTimeDao {

    @Query("Select * from " + StarContract.StopTimes.CONTENT_PATH)
    List<StopTimeEntity> getStopTimeList();


    @Query("SELECT DISTINCT "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN
            + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
            + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stop_id"
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id"
            + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.MONDAY + " = 1"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrival_time"
            + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
            + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getStopTimeCursorMonday(String route_id, String stop_id, String direction_id, String arrival_time);//, String dayOfWeek);


    @Query("SELECT DISTINCT "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
            + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
            + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stop_id"
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id"
            + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.TUESDAY + " = 1"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrival_time"
            + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
            + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getStopTimeCursorTuesday(String route_id, String stop_id, String direction_id, String arrival_time);//, String dayOfWeek);

    @Query("SELECT DISTINCT "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
            + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
            + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stop_id"
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id"
            + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.WEDNESDAY + " = 1"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrival_time"
            + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
            + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getStopTimeCursorWenesday(String route_id, String stop_id, String direction_id, String arrival_time);//, String dayOfWeek);


    @Query("SELECT DISTINCT "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
            + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
            + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stop_id"
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id"
            + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.THURSDAY + " = 1"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrival_time"
            + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
            + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getStopTimeCursorThursday(String route_id, String stop_id, String direction_id, String arrival_time);//, String dayOfWeek);


    @Query("SELECT DISTINCT "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
            + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
            + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stop_id"
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id"
            + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.FRIDAY + " = 1"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrival_time"
            + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
            + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getStopTimeCursorFriday(String route_id, String stop_id, String direction_id, String arrival_time);//, String dayOfWeek);


    @Query("SELECT DISTINCT "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
            + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
            + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stop_id"
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id"
            + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.SATURDAY + " = 1"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrival_time"
            + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
            + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getStopTimeCursorSaturday(String route_id, String stop_id, String direction_id, String arrival_time);//, String dayOfWeek);


    @Query("SELECT DISTINCT "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + ", "
            + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.START_DATE + ", "
            + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.END_DATE
            + " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH + ", " + StarContract.Calendar.CONTENT_PATH
            + " WHERE " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.SERVICE_ID + "=" + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns._ID
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = :stop_id"
            + " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id"
            + " AND " + StarContract.Calendar.CONTENT_PATH + "." + StarContract.Calendar.CalendarColumns.SUNDAY + " = 1"
            + " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrival_time"
            + " GROUP BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
            + " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getStopTimeCursorSunday(String route_id, String stop_id, String direction_id, String arrival_time);//, String dayOfWeek);


    @Query("SELECT DISTINCT "
            + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + ", "
            + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME +
            " FROM " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Stops.CONTENT_PATH +
            " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns._ID + "=" + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID +
            " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID + " = :tripId" +
            " AND " + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " > :arrivalTime" +
            " ORDER BY " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)
    Cursor getRouteDetail(String tripId, String arrivalTime);

    @Insert
    void insertAll(ArrayList<StopTimeEntity> stopTimeEntities);

    @Query("DELETE FROM " + StarContract.StopTimes.CONTENT_PATH)
    void deleteAll();
}
