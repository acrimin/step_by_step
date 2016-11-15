package com.example.madisonmaddox.prototype_step_by_step;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("step_by_step", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", 0);
        if (user_id != 0) {
            dm = new DatabaseManager(user_id, sharedPreferences.getString("name", ""));
            /*
            try {
            //    dm.updateDatabase(getApplicationContext());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            */
            nextPage();
        }
    }

    public void signInClick(View view) {
        DatabaseManager dm = new DatabaseManager("1234567890");

        SharedPreferences sharedPreferences = this.getSharedPreferences("step_by_step", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        while (dm.getUser_id() == 0) {}

        editor.putInt("user_id", dm.getUser_id()).apply();
        editor.putString("name", dm.getName()).apply();

        /*
        try {
            dm.updateDatabase(getApplicationContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */

        nextPage();

    }

    private void nextPage() {
        Intent nextScreen = new Intent(getApplicationContext(), SuggestedTasks.class);
        startActivity(nextScreen);
    }


}
