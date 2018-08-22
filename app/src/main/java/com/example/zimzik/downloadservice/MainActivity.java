package com.example.zimzik.downloadservice;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<String> IMAGES_LINKS = Arrays.asList(
            "https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/34950/pexels-photo.jpg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/257840/pexels-photo-257840.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/210186/pexels-photo-210186.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/36717/amazing-animal-beautiful-beautifull.jpg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/459301/pexels-photo-459301.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/132419/pexels-photo-132419.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/128458/pexels-photo-128458.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/395132/pexels-photo-395132.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/462146/pexels-photo-462146.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/240040/pexels-photo-240040.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/457937/pexels-photo-457937.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/33707/flowers-meadow-wood-forest.jpg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/417191/pexels-photo-417191.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/340932/pexels-photo-340932.jpeg?auto=compress&cs=tinysrgb&h=350",
            "https://images.pexels.com/photos/295771/pexels-photo-295771.jpeg?auto=compress&cs=tinysrgb&h=350"
    );

    RecyclerView mRecyclerView;
    ImageListAdapter mImageListAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_images);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mImageListAdapter = new ImageListAdapter(IMAGES_LINKS);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mImageListAdapter);
    }
}
