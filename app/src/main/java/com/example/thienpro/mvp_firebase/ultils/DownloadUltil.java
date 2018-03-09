package com.example.thienpro.mvp_firebase.ultils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.util.UUID;

public class DownloadUltil {
    private static boolean isDownloadManagerAvailable(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }

    public static void startDownload(Context context, String urlImage) {
        if (isDownloadManagerAvailable(context)) {
            String url = urlImage;
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Đang tải");
            request.setTitle(UUID.randomUUID().toString());
            // in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "picture");

            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }
    }
}
