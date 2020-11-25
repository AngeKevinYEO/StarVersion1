package fr.istic.starv1KM;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;

import fr.istic.starv1KM.database.BusRoute;
import fr.istic.starv1KM.database.CalendarEntity;
import fr.istic.starv1KM.database.StarDatabase;
import fr.istic.starv1KM.database.StopEntity;
import fr.istic.starv1KM.database.StopTimeEntity;
import fr.istic.starv1KM.database.TripEntity;

public class FillDbWorker extends Worker {

    private Context context;
    private ProgressManager progressManager;

    public FillDbWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        progressManager = new ProgressManager(context, context.getString(R.string.db_loading_status_message), 100, false);
    }

    @NonNull
    @Override
    public Result doWork() {
        progressManager.getBuilder().setProgress(100, 100, true);
        progressManager.getNotifiationManager().notify(1, progressManager.getBuilder().build());
        clearDatabase();
        fillDatabase();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);

        return Result.success();
    }

    /**
     *Efface la base de données des données précédentes
     */
    private void clearDatabase() {
        StarDatabase.getInstance(context).routeDao().deleteAll();
    }

    /**
     *Remplit la base de données avec les nouvelles données
     */
    private void fillDatabase() {

        TxtFilesReader txtFilesReader = new TxtFilesReader(context.getExternalFilesDir(null).toString());

        ArrayList<BusRoute> routeEntities = (ArrayList<BusRoute>) txtFilesReader.readEntitiesFromFile(Constants.ROUTES_FILE, BusRoute.class);
        StarDatabase.getInstance(context).routeDao().insertAll(routeEntities);

        ArrayList<TripEntity> tripEntities = (ArrayList<TripEntity>) txtFilesReader.readEntitiesFromFile(Constants.TRIPS_FILE, TripEntity.class);
        StarDatabase.getInstance(context).tripDao().insertAll(tripEntities);

        ArrayList<CalendarEntity> calendarEntities = (ArrayList<CalendarEntity>) txtFilesReader.readEntitiesFromFile(Constants.CALENDAR_FILE, CalendarEntity.class);
        StarDatabase.getInstance(context).calendarDao().insertAll(calendarEntities);

        ArrayList<StopEntity> stopEntities = (ArrayList<StopEntity>) txtFilesReader.readEntitiesFromFile(Constants.STOPS_FILE, StopEntity.class);
        StarDatabase.getInstance(context).stopDao().insertAll(stopEntities);

        ArrayList<StopTimeEntity> stopTimeEntities = (ArrayList<StopTimeEntity>) txtFilesReader.readEntitiesFromFile(Constants.STOP_TIME_FILE, StopTimeEntity.class);
        StarDatabase.getInstance(context).stopTimeDao().insertAll(stopTimeEntities);
    }

}
