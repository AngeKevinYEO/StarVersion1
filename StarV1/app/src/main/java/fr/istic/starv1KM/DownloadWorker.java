package fr.istic.starv1KM;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadWorker extends Worker {

    private Context context;
    private WorkerParameters workerParams;
    private DownloadManager downloadManager;
    private long downloadId;
    private ProgressManager progressManager;
    private NotificationManager manager;

    /**
     * Détecter lorsqu'un téléchargement est terminé, puis exécuter le processus de décompression du fichier..
     */
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadId == id) {
                OneTimeWorkRequest saveRequest =
                        new OneTimeWorkRequest.Builder(UnzipWorker.class)
                                .build();
                WorkManager.getInstance(getApplicationContext())
                        .enqueue(saveRequest);
            }
        }
    };


    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParams = workerParams;
        context.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        progressManager = new ProgressManager(context, context.getString(R.string.timetables_download_status_message), 100, true);
    }

    @NonNull
    @Override
    public Result doWork() {
        downloadFile(workerParams.getInputData().getString("uri"));
        showProgress();

        return Result.success();
    }

    private void downloadFile(String uri) {
        File file = new File(context.getExternalFilesDir(null), "starTimetables");
        if (uri != null) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri))
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                    .setDestinationUri(Uri.fromFile(file))
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
            downloadManager = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
            downloadId = downloadManager.enqueue(request);
        }
    }

    private void showProgress() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean downloading = true;
                int bytesDownloaded = 0;
                int bytesTotal = 0;
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);
                    Cursor cursor = downloadManager.query(q);
                    if (cursor != null && cursor.moveToFirst()) {
                        bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                        }
                    }

                    final double downloadProgress = (int) ((bytesDownloaded * 100l) / bytesTotal);

                    progressManager.getBuilder().setProgress(100, (int) downloadProgress, false);
                    progressManager.getNotifiationManager().notify(1, progressManager.getBuilder().build());

                    cursor.close();
                }
                progressManager.getNotifiationManager().cancel(1);
                context.unregisterReceiver(onDownloadComplete);
            }

        }).start();
    }

}
