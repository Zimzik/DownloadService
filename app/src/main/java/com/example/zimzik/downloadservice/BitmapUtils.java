package com.example.zimzik.downloadservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BitmapUtils {

    private static final String TAG = BitmapUtils.class.getSimpleName();

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static ByteArrayOutputStream downloadBitmapFromUrl(URL url, BitmapUtils.BitmapUtilsCallback bitmapUtilsCallback) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            InputStream is = new BufferedInputStream(url.openStream());
            byte[] buff = new byte[1024];
            int n;
            while ((n = is.read(buff)) != -1 && !bitmapUtilsCallback.isInterrupted()) {
                baos.write(buff, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmapUtilsCallback.isInterrupted()) {
            return null;
        }
        return baos;
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] bytes, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private static int getFileSize(URL url) {
        int fileSize = 0;
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            fileSize = urlConnection.getContentLength();
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        }
        return fileSize;
    }

    public static void downloadImage(String link, int position,  BitmapUtils.BitmapUtilsCallback callback, GetBitmap getBitmap) {
        executorService.submit(() -> {
            try {
                URL url = new URL(link);
                ByteArrayOutputStream byteArrayOutputStream = BitmapUtils.downloadBitmapFromUrl(url, callback);
                if (byteArrayOutputStream != null) {
                    Log.i(TAG, "downloadImage: Start to download Bitmap #" + position);
                    Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromByteArray(byteArrayOutputStream.toByteArray(), 100, 100);
                    getBitmap.get(bitmap);
                }
            } catch (IOException e) {
                Log.i(TAG, e.getMessage());
            }
        });
    }


    public interface GetBitmap {
        void get(Bitmap bitmap);
    }

    public interface BitmapUtilsCallback {
        boolean isInterrupted();
    }
}
