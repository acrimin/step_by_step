package com.example.madisonmaddox.prototype_step_by_step;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    public Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newPage = (Button)findViewById(R.id.but1);

        newPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), SuggestedTasks.class);

                startActivity(nextScreen);
            }
        });
    }
}
