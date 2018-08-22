package com.example.zimzik.downloadservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ImageDownloadService extends Service {

    private final IBinder mBinder = new LocalBinder();

    public ImageDownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        ImageDownloadService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ImageDownloadService.this;
        }
    }
}
