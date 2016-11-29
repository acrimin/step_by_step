package com.example.madisonmaddox.prototype_step_by_step;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Button goSuggested = (Button) findViewById(R.id.button6);
        Button goAll = (Button) findViewById(R.id.button7);

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
    }
}
