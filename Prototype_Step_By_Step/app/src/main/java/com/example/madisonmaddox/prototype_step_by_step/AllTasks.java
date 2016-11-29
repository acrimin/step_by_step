package com.example.madisonmaddox.prototype_step_by_step;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.example.madisonmaddox.prototype_step_by_step.R.id.imageView;

public class AllTasks extends Activity {

    private ImageAdapter mAdapter;
    private ArrayList<String> listTaskNames;
    private ArrayList<Integer> listPics;

    private GridView gridView;

    public Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("step_by_step", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", 0);

        prepareList();

        Toast.makeText(this, "user_id: " + user_id, Toast.LENGTH_LONG).show();

        Button newPage = (Button) findViewById(R.id.button3);

        newPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), SuggestedTasks.class);
                startActivity(nextScreen);
            }
        });

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new ImageAdapter(this,listTaskNames, listPics);

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Toast.makeText(AllTasks.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                Intent goTask = new Intent(getApplicationContext(), Task.class);
                startActivity(goTask);
            }
        });
    }

    public void prepareList()
    {
        listTaskNames = new ArrayList<String>();

        listTaskNames.add("Dishes");
        listTaskNames.add("Stocking Shelves");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");
        listTaskNames.add("Laundry");


        listPics = new ArrayList<Integer>();

        listPics.add(R.drawable.dishes);
        listPics.add(R.drawable.shelves);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
        listPics.add(R.drawable.laundry_clip_art);
    }
}

