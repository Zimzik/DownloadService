package com.example.zimzik.downloadservice;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> implements BitmapUtils.BitmapUtilsCallback {
    private List<String> mImagesLinks;
    private LruCache<String, Bitmap> mMemoryCache;
    private static final String TAG = ImageListAdapter.class.getSimpleName();

    public ImageListAdapter(List<String> imagesLinks) {
        mImagesLinks = imagesLinks;
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap = mMemoryCache.get(mImagesLinks.get(position));
        if (bitmap == null) {
            BitmapUtils.downloadImage(mImagesLinks.get(position), position,this, downloadedBitmap -> {
                Log.i(TAG, "onBindViewHolder: hello from callback on position " + position);
                Log.i(TAG, "onBindViewHolder: IV hash: " + holder.image.hashCode());
                holder.image.setImageBitmap(downloadedBitmap);
                Log.i(TAG, "onBindViewHolder: set image on position " + position);
                holder.progressBar.setVisibility(View.INVISIBLE);
                Log.i(TAG, "onBindViewHolder: progress bar invisible on position " + position);
                mMemoryCache.put(mImagesLinks.get(position), downloadedBitmap);
            });

        } else {
            Log.i(TAG, "onBindViewHolder: Bitmap " + position + " got from cache");
            holder.image.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mImagesLinks.size();
    }

    @Override
    public boolean isInterrupted() {
        return false;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_donwloaded_image);
            progressBar = itemView.findViewById(R.id.pb_download_image);
        }
    }
}
