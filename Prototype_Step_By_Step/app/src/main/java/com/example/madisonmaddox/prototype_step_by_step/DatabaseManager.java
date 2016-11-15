package com.example.madisonmaddox.prototype_step_by_step;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by acrimin on 11/15/2016.
 */

public class DatabaseManager {
    private String LOGIN_PHP = "https://people.cs.clemson.edu/~acrimin/login.php/";
    private String LOCATION_PHP = "https://people.cs.clemson.edu/~acrimin/getLocation.php/";
    private String TASK_PHP = "https://people.cs.clemson.edu/~acrimin/getTasks.php/";
    private String TASK_STEPS_PHP = "https://people.cs.clemson.edu/~acrimin/getTaskSteps.php/";
    private String TASK_VIDEO_PHP = "https://people.cs.clemson.edu/~acrimin/getTaskVideos.php/";



    private int user_id;
    private String name;

    DatabaseManager(String phoneNumber) {
        user_id = 0;
        name = "";
        login(phoneNumber);
    }

    DatabaseManager(int user_id, String name) {
        this.user_id = user_id;
        this.name = name;
    }

    private void login(String phoneNumber) {
        String result = "";
        String url = LOGIN_PHP + "?phone=" + phoneNumber;
        try {
            result = new PHPAccess().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONArray array = new JSONArray(result);
            JSONObject object = array.getJSONObject(0);
            user_id = object.getInt("user_id");
            name = object.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateDatabase(Context context) throws InterruptedException, ExecutionException, JSONException {
        updateLocationDatabase(context);
    }

    private void updateLocationDatabase(Context context) throws ExecutionException, InterruptedException, JSONException {
        String results = "";
        String url = LOCATION_PHP + "?user_id=" + user_id;

        results = new PHPAccess().execute(url).get();
        JSONArray array = new JSONArray(results);

        SQLiteDatabase db = context.openOrCreateDatabase("step_by_step", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS location (" +
                "location_id INTEGER(11) PRIMARY KEY " +
                "name VARCHAR " +
                "latitude REAL " +
                "longitude REAL "
        );

        int location_id;
        String name;
        double latitude;
        double longitude;
        Log.i("Results", "loc   name");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            location_id = object.getInt("location_id");
            name = object.getString("name");
            latitude = object.getDouble("latitude");
            longitude = object.getDouble("longitude");

            String sql = "SELECT location_id FROM location WHERE location_id = ?";

            Cursor c = db.rawQuery(sql, new String[]{String.valueOf(location_id)});
            Log.i("Count", "location: " + c.getCount());
            Log.i("Results", location_id + "   " + name);

            Log.i("SQL", sql);
            sql = "INSERT INTO location VALUES(" +
                    location_id + ", " +
                    "'" + name + "', " +
                    latitude + ", " +
                    longitude + ")";
            db.execSQL(sql);
        }
    }

    private class PHPAccess extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try {
                URL url = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                String line;
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    response = sb.toString();
                }
                br.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public String getName() {
        return name;
    }

    public int getUser_id() {
        return user_id;
    }
}
