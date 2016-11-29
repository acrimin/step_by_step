package com.example.madisonmaddox.prototype_step_by_step.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.madisonmaddox.prototype_step_by_step.Model.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by acrimin on 11/28/2016.
 */

class LocationDatabase {
    private String LOCATION_PHP = "https://people.cs.clemson.edu/~acrimin/getLocation.php/";

    public void update(SQLiteDatabase db, int user_id) throws ExecutionException, InterruptedException, JSONException {
        String results = "";
        String url = LOCATION_PHP + "?user_id=" + user_id;

        results = new PHPAccess().execute(url).get();
        Log.i("Database JSON", "Location: " + results);
        JSONArray array = new JSONArray(results);

        db.execSQL("CREATE TABLE IF NOT EXISTS location (" +
                "location_id INTEGER(11) PRIMARY KEY, " +
                "name VARCHAR, " +
                "latitude REAL, " +
                "longitude REAL)"
        );

        int location_id;
        String name;
        double latitude;
        double longitude;
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            location_id = object.getInt("location_id");
            name = object.getString("name");
            latitude = object.getDouble("latitude");
            longitude = object.getDouble("longitude");

            String sql = "SELECT location_id FROM location WHERE location_id = ?";

            String[] args = new String[1];
            args[0] = String.valueOf(location_id);
            Cursor c = db.rawQuery(sql, args);
            if (c.getCount() > 0)
                continue;

            sql = "INSERT INTO location VALUES(" +
                    location_id + ", " +
                    "'" + name + "', " +
                    latitude + ", " +
                    longitude + ")";

            db.execSQL(sql);
        }
    }

    public Location[] getAllLocations(SQLiteDatabase db) {
        String sql = "SELECT name, latitude, longitude FROM location";

        Cursor c =  db.rawQuery(sql, null);

        ArrayList<Location> list = new ArrayList<Location>();
        if (c.moveToFirst()) {
            do {
                list.add(new Location(c.getString(0), c.getDouble(1), c.getDouble(2)));
            } while (c.moveToNext());
        }

        Location[] locations = new Location[list.size()];
        locations = list.toArray(locations);
        return locations;
    }
}
