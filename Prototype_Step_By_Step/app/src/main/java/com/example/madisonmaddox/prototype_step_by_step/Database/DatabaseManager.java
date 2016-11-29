package com.example.madisonmaddox.prototype_step_by_step.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.madisonmaddox.prototype_step_by_step.Model.Location;
import com.example.madisonmaddox.prototype_step_by_step.Model.Step;
import com.example.madisonmaddox.prototype_step_by_step.Model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by acrimin on 11/15/2016.
 */

/* Singleton handles database stuff */
public class DatabaseManager {
    private String LOGIN_PHP = "https://people.cs.clemson.edu/~acrimin/login.php/";

    private LocationDatabase locationDatabase;
    private TaskDatabase taskDatabase;
    private TaskStepsDatabase taskStepsDatabase;
    private TaskVideoDatabase taskVideoDatabase;

    private SQLiteDatabase db;
    private Context context;

    SharedPreferences sharedPreferences;

    private int user_id;
    public int getUser_id() {
        return user_id;
    }

    private String name;
    public String getName() {
        return name;
    }

    private static DatabaseManager instance = null;

    private DatabaseManager() { }

    public static DatabaseManager getInstance() {
        if (instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public void login(String phoneNumber) throws InterruptedException, ExecutionException, JSONException {
        String url = LOGIN_PHP + "?phone=" + phoneNumber;
        String result = new PHPAccess().execute(url).get();

        JSONArray array = new JSONArray(result);
        JSONObject object = array.getJSONObject(0);
        user_id = object.getInt("user_id");
        name = object.getString("name");

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("user_id", user_id).apply();
        edit.putString("name", name).apply();

        update();
    }

    /* if return is false needs login */
    public boolean init(Context context) throws InterruptedException, ExecutionException, JSONException {
        sharedPreferences = context.getSharedPreferences("step_by_step", context.MODE_PRIVATE);
        db = context.openOrCreateDatabase("step_by_step", Context.MODE_PRIVATE, null);
        user_id = sharedPreferences.getInt("user_id", -1);
        name = sharedPreferences.getString("name", "");
        this.context = context;

        locationDatabase = new LocationDatabase();
        taskDatabase = new TaskDatabase();
        taskStepsDatabase = new TaskStepsDatabase();
        taskVideoDatabase = new TaskVideoDatabase();

        if (user_id == -1)
            return false;

        db = context.openOrCreateDatabase("step_by_step", Context.MODE_PRIVATE, null);
        update();

        return true;
    }

    public void update() throws InterruptedException, ExecutionException, JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("step_by_step", context.MODE_PRIVATE);
        String updated = sharedPreferences.getString("updated", "0000-00-00");
        Log.i("Database", "Updated Orig: " + updated);

        try {
            locationDatabase.update(db, user_id);
            taskDatabase.update(db, user_id, updated);
            taskStepsDatabase.update(db, user_id, updated);
//            taskVideoDatabase.update(db, user_id, updated);
        } catch (Exception e) {
            Log.i("Database", "resetting " + updated);
            Toast.makeText(context, "Resetting Database", Toast.LENGTH_LONG).show();

            context.deleteDatabase("step_by_step");
            db = context.openOrCreateDatabase("step_by_step", Context.MODE_PRIVATE, null);

            updated = "0000-00-00";

            locationDatabase.update(db, user_id);
            taskDatabase.update(db, user_id, updated);
            taskStepsDatabase.update(db, user_id, updated);
//            taskVideoDatabase.update(db, user_id, updated);
        }

        Calendar calendar = Calendar.getInstance();
        updated =  String.valueOf(calendar.get(Calendar.YEAR)) + "-";
        updated += String.valueOf(calendar.get(Calendar.MONTH)+1) + "-";
        updated += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        Log.i("Database", "Updated After: " + updated);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("updated", updated).apply();
    }

    public Location[] getAllLocations() {
        return locationDatabase.getAllLocations(db);
    }

    public Task[] getAllTasks() {
        return taskDatabase.getAllTasks(db);
    }

    public Step[] getAllSteps(Task task) {
        return taskStepsDatabase.getSteps(db, task);
    }


}
