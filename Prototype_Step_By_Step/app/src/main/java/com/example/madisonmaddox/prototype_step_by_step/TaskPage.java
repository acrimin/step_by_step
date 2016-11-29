package com.example.madisonmaddox.prototype_step_by_step;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TaskPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Button goSuggested = (Button) findViewById(R.id.button6);
        Button goAll = (Button) findViewById(R.id.button7);
        ImageButton goStepByStep = (ImageButton) findViewById(R.id.imageButton);
        ImageButton goVideo = (ImageButton) findViewById(R.id.videoBtn);

        goSuggested.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), SuggestedTasks.class);
                startActivity(nextScreen);
            }
        });

        goAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), AllTasks.class);
                startActivity(nextScreen);
            }
        });

        goStepByStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), MiddleStep.class);
                startActivity(nextScreen);
            }
        });

        goVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=aOKFUdPEYg4")));
                Log.i("Video", "Video Playing....");
            }
        });
    }
}
