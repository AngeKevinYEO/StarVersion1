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
public interface StopDao {

    @Query("Select * from " + StarContract.Stops.CONTENT_PATH)
    List<StopEntity> getStopList();

    @Query("Select * from " + StarContract.Stops.CONTENT_PATH)
    Cursor getStopListCursor();



    @Query("SELECT DISTINCT " +
            StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + ", " +
            StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns._ID + ", " +
            StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.HEADSIGN + ", " +
            StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID +
            " FROM " + StarContract.Stops.CONTENT_PATH + ", " + StarContract.StopTimes.CONTENT_PATH + ", " + StarContract.Trips.CONTENT_PATH +
            " WHERE " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns._ID + " = " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.TRIP_ID +
            " AND " + StarContract.StopTimes.CONTENT_PATH + "." + StarContract.StopTimes.StopTimeColumns.STOP_ID + " = " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns._ID +
            " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.ROUTE_ID + " = :route_id" +
            " AND " + StarContract.Trips.CONTENT_PATH + "." + StarContract.Trips.TripColumns.DIRECTION_ID + " = :direction_id" +
            " ORDER BY " + StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)
    Cursor getStopsByLines(String route_id, String direction_id);

    @Query("SELECT DISTINCT " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME +
            " FROM " + StarContract.Stops.CONTENT_PATH +
            " WHERE " + StarContract.Stops.CONTENT_PATH + "." + StarContract.Stops.StopColumns.NAME + " LIKE :char_sequence || '%' ORDER BY " + StarContract.Stops.StopColumns.NAME + " ASC")
    Cursor getSearchedStops(String char_sequence);

    @Insert
    void insertAll(ArrayList<StopEntity> stopEntities);

    @Query("DELETE FROM " + StarContract.Stops.CONTENT_PATH)
    void deleteAll();
}
