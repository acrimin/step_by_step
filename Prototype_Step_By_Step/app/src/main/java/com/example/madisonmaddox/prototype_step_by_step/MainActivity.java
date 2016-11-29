package com.example.madisonmaddox.prototype_step_by_step;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.madisonmaddox.prototype_step_by_step.Database.DatabaseManager;
import com.example.madisonmaddox.prototype_step_by_step.Model.*;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import static android.R.attr.button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (DatabaseManager.getInstance().init(getApplicationContext())) { // already logged in from previous
                //toSuggested(null);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void toSuggested(View view) {
        try {   // login
            DatabaseManager.getInstance().login("1234567890");
            Task[] tasks = DatabaseManager.getInstance().getAllTasks();
            Location[] locations = DatabaseManager.getInstance().getAllLocations();
            for (int i = 0; i < locations.length; i++) {
                Log.i("Database Results", locations[i].toString());
            }
            for (int i = 0; i < tasks.length; i++) {
                Log.i("Database Results", tasks[i].toString());
                Step[] steps = tasks[i].getSteps();
                for (int x = 0; x < steps.length; x++) {
                    Log.i("Database Results", steps[x].toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent nextScreen = new Intent(getApplicationContext(), SuggestedTasks.class);
        startActivity(nextScreen);
    }


}
