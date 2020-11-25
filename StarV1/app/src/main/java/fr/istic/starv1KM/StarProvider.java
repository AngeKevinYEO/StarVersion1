package fr.istic.starv1KM;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import fr.istic.starv1KM.database.CalendarDao;
import fr.istic.starv1KM.database.RouteDao;
import fr.istic.starv1KM.database.StarContract;
import fr.istic.starv1KM.database.StarDatabase;
import fr.istic.starv1KM.database.StopDao;
import fr.istic.starv1KM.database.StopTimeDao;
import fr.istic.starv1KM.database.TripDao;

/**
 * @Version 1.0
 * @Author YEO KEVIN | BAMBA MOUSSA
 */
public class StarProvider extends ContentProvider {

    private static final int QUERY_ROUTES = 1;
    private static final int QUERY_TRIPS = 2;
    private static final int QUERY_STOPS = 3;
    private static final int QUERY_STOP_TIMES = 4;
    private static final int QUERY_CALENDAR = 5;
    private static final int QUERY_ROUTES_DETAILS = 6;
    private static final int QUERY_SEARCHED_STOPS = 7;
    private static final int QUERY_ROUTES_FOR_STOP = 8;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.BusRoutes.CONTENT_PATH, 1);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Trips.CONTENT_PATH, 2);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Stops.CONTENT_PATH, 3);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.StopTimes.CONTENT_PATH, 4);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.Calendar.CONTENT_PATH, 5);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.RouteDetails.CONTENT_PATH, 6);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.SearchedStops.CONTENT_PATH, 7);
        URI_MATCHER.addURI(StarContract.AUTHORITY, StarContract.RoutesForStop.CONTENT_PATH, 8);
    }

    private StarDatabase starDatabase;

    @Override
    public boolean onCreate() {
        starDatabase = Room.databaseBuilder(getContext(), StarDatabase.class, StarDatabase.DB_NAME).build();
        return true;
    }

    /**
     *
     * @param uri
     * @param projection
     * @param selection clause (où) à mettre dans la requête SQL
     * @param selectionArgs valeurs auxquelles les sélections doivent être égales
     * @param sortOrder ordre de tri pour ordonner le résultat
     * @return résultat de la base de données
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor result = null;
        System.out.println(uri);
        int uriMatcher = URI_MATCHER.match(uri);
        if (uriMatcher == QUERY_ROUTES) {
            RouteDao routeDao = StarDatabase.getInstance(getContext()).routeDao();
            result = routeDao.getRouteListCursor();
        } else if (uriMatcher == QUERY_TRIPS) {
            TripDao tripDao = StarDatabase.getInstance(getContext()).tripDao();
            result = tripDao.getTripsListCursor(selectionArgs[0]);
        } else if (uriMatcher == QUERY_STOPS) {
            StopDao stopDao = StarDatabase.getInstance(getContext()).stopDao();
            result = stopDao.getStopsByLines(selectionArgs[0], selectionArgs[1]);
        } else if (uriMatcher == QUERY_STOP_TIMES) {
            // it's not possible to put a column name in parameter of a query such as :
            // "where :dayOfWeek = 1" where :dayOfWeek is a method's parameter with a value of monday or tuesday...
            // So we have to do 7 separated queries with only one different "where clause" to
            // indicates the wanted day.
            if (selectionArgs.length > 2 && !selectionArgs[3].isEmpty()) {
                String dayOfWeek = selectionArgs[3];
                StopTimeDao stopTimeDao = StarDatabase.getInstance(getContext()).stopTimeDao();
                if (dayOfWeek.equals(StarContract.Calendar.CalendarColumns.MONDAY)) {
                    result = stopTimeDao.getStopTimeCursorMonday(selectionArgs[0], selectionArgs[1], selectionArgs[2], selectionArgs[4]);
                } else if (dayOfWeek.equals(StarContract.Calendar.CalendarColumns.TUESDAY)) {
                    result = stopTimeDao.getStopTimeCursorTuesday(selectionArgs[0], selectionArgs[1], selectionArgs[2], selectionArgs[4]);
                } else if (dayOfWeek.equals(StarContract.Calendar.CalendarColumns.WEDNESDAY)) {
                    result = stopTimeDao.getStopTimeCursorWenesday(selectionArgs[0], selectionArgs[1], selectionArgs[2], selectionArgs[4]);
                } else if (dayOfWeek.equals(StarContract.Calendar.CalendarColumns.THURSDAY)) {
                    result = stopTimeDao.getStopTimeCursorThursday(selectionArgs[0], selectionArgs[1], selectionArgs[2], selectionArgs[4]);
                } else if (dayOfWeek.equals(StarContract.Calendar.CalendarColumns.FRIDAY)) {
                    result = stopTimeDao.getStopTimeCursorFriday(selectionArgs[0], selectionArgs[1], selectionArgs[2], selectionArgs[4]);
                } else if (dayOfWeek.equals(StarContract.Calendar.CalendarColumns.SATURDAY)) {
                    result = stopTimeDao.getStopTimeCursorSaturday(selectionArgs[0], selectionArgs[1], selectionArgs[2], selectionArgs[4]);
                } else if (dayOfWeek.equals(StarContract.Calendar.CalendarColumns.SUNDAY)) {
                    result = stopTimeDao.getStopTimeCursorSunday(selectionArgs[0], selectionArgs[1], selectionArgs[2], selectionArgs[4]);
                }
            }
        } else if (uriMatcher == QUERY_CALENDAR) {
            CalendarDao calendarDao = StarDatabase.getInstance(getContext()).calendarDao();
            result = calendarDao.getCalendarListCursor();
        } else if (uriMatcher == QUERY_ROUTES_DETAILS) {
            StopTimeDao stopTimeDao = StarDatabase.getInstance(getContext()).stopTimeDao();
            result = stopTimeDao.getRouteDetail(selectionArgs[0], selectionArgs[1]);
        }
        else if (uriMatcher == QUERY_SEARCHED_STOPS) {
            StopDao stopDao = StarDatabase.getInstance(getContext()).stopDao();
            result = stopDao.getSearchedStops(selectionArgs[0]);
        }
        else if (uriMatcher == QUERY_ROUTES_FOR_STOP) {
            RouteDao routeDao = StarDatabase.getInstance(getContext()).routeDao();
            result = routeDao.getRoutesForStop(selectionArgs[0]);
        } else {
            throw new IllegalArgumentException("Unknow URI : " + uri);
        }
        return result;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String type = null;
        int uriMatcher = URI_MATCHER.match(uri);
        if (uriMatcher == QUERY_ROUTES) {
            type = StarContract.BusRoutes.CONTENT_ITEM_TYPE;
        } else if (uriMatcher == QUERY_TRIPS) {
            type = StarContract.Trips.CONTENT_ITEM_TYPE;
        } else if (uriMatcher == QUERY_STOPS) {
            type = StarContract.Stops.CONTENT_ITEM_TYPE;
        } else if (uriMatcher == QUERY_STOP_TIMES) {
            type = StarContract.StopTimes.CONTENT_ITEM_TYPE;
        } else if (uriMatcher == QUERY_CALENDAR) {
            type = StarContract.Calendar.CONTENT_ITEM_TYPE;
        } else if (uriMatcher == QUERY_ROUTES_DETAILS) {
            type = StarContract.RouteDetails.CONTENT_ITEM_TYPE;
        } else if(uriMatcher == QUERY_SEARCHED_STOPS) {
            type = StarContract.SearchedStops.CONTENT_ITEM_TYPE;
        }
        else if(uriMatcher == QUERY_ROUTES_FOR_STOP) {
            type = StarContract.RoutesForStop.CONTENT_ITEM_TYPE;
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
