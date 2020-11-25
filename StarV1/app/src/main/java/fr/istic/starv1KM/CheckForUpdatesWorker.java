package fr.istic.starv1KM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;



public class CheckForUpdatesWorker extends Worker {

    private JSONObject json;
    private String oldJson;
    private String uriToZip;
    private SharedPreferences prefs;


    public CheckForUpdatesWorker(Context context, WorkerParameters params) {
        super(context, params);
        prefs = getApplicationContext().getSharedPreferences("fr.istic.starv1KM", Context.MODE_PRIVATE);
        oldJson = prefs.getString("oldJson", null);
        json = new JSONObject();
        prefs.edit().putBoolean("newTimetablesAvailable", false).apply();
    }

    @Override
    public Result doWork() {
        Result result;
        try {
            json = getJsonObjectFromUrl(Constants.URL);
            JSONArray jsonArray = json.optJSONArray("records");
            if (!json.toString().equals(oldJson)) {
                try {
                    Date currentDate = new Date();
                    Date beginDateFirstZip = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).parse(jsonArray.getJSONObject(0).getJSONObject("fields").getString("debutvalidite"));
                    Date endDateFirstZip = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).parse(jsonArray.getJSONObject(0).getJSONObject("fields").getString("finvalidite"));
                    Date beginDateSecondZip = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).parse(jsonArray.getJSONObject(1).getJSONObject("fields").getString("debutvalidite"));
                    Date endDateSecondZip = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).parse(jsonArray.getJSONObject(1).getJSONObject("fields").getString("finvalidite"));
                    if ((currentDate.equals(beginDateFirstZip) || currentDate.after(beginDateFirstZip)) && (currentDate.equals(endDateFirstZip) || currentDate.before(endDateFirstZip))) {
                        uriToZip = jsonArray.getJSONObject(0).getJSONObject("fields").getString("url");
                    } else if ((currentDate.equals(beginDateSecondZip) || currentDate.after(beginDateSecondZip)) && (currentDate.equals(endDateSecondZip) || currentDate.before(endDateSecondZip))) {
                        uriToZip = jsonArray.getJSONObject(1).getJSONObject("fields").getString("url");
                    }
                    if (oldJson != null) {
                        oldJson = json.toString();
                        prefs.edit().putString("oldJson", oldJson).apply();
                        showNotification(getApplicationContext().getString(R.string.notification_title), getApplicationContext().getString(R.string.new_timetables_notification_desc));
                        prefs.edit().putBoolean("newTimetablesAvailable", true).apply();
                    } else {
                        oldJson = json.toString();
                        prefs.edit().putString("oldJson", oldJson).apply();
                        prefs.edit().putBoolean("newTimetablesAvailable", true).apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("uriToZip", uriToZip);
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                    prefs.edit().putString("uriToZip", uriToZip).apply();
                    oldJson = json.toString();
                    prefs.edit().putString("oldJson", oldJson).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                prefs.edit().putBoolean("newTimetablesAvailable", false).apply();
                uriToZip = prefs.getString("uriToZip", null);
            }
            result = Result.success();
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("test");
            result = Result.failure();
        }

        return result;

    }

    /**
     * Renvoie un json à partir d'une URL donnée.
     * Utilisé pour obtenir le json qui contient des informations sur les horaires actuels disponibles (Période de validité ...).
     *
     * @param urlString l'url vers le json
     * @throws JSONException
     */
    private JSONObject getJsonObjectFromUrl(String urlString) throws JSONException {
        HttpURLConnection urlConnection;
        String line;
        StringBuilder jsonString = new StringBuilder();

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return new JSONObject(jsonString.toString());
    }

    /**

     * Affiche une notification pour demander à l'utilisateur s'il souhaite mettre à jour les horaires vers une nouvelle version.
     * Si l'utilisateur clique sur la notification, l'application est mise au premier plan et les nouveaux horaires sont téléchargés.
     * @param title la vignette de notification
     * @param desc la description de la notification
     */
    private void showNotification(String title, String desc) {

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "task_channel";
        String channelName = "task_name";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        Intent downloadIntent = new Intent(getApplicationContext(), MainActivity.class);
        downloadIntent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        downloadIntent.putExtra("uriToZip", uriToZip);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addNextIntent(downloadIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        manager.notify(1, builder.build());

    }
}
