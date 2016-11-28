package com.example.madisonmaddox.prototype_step_by_step;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

//import com.example.madisonmaddox.prototype_step_by_step.customAdapter;

public class SuggestedTasks extends AppCompatActivity {

    public Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_tasks);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("step_by_step", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", 0);

        Toast.makeText(this, "user_id: " + user_id, Toast.LENGTH_LONG).show();

        Button newPage = (Button) findViewById(R.id.button2);

        newPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), AllTasks.class);

                startActivity(nextScreen);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v,
        int position, long id) {
            Toast.makeText(SuggestedTasks.this, "" + position, Toast.LENGTH_SHORT).show();
        }
    });
}

}
