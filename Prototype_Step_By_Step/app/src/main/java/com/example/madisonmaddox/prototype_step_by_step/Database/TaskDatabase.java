package com.example.madisonmaddox.prototype_step_by_step.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.madisonmaddox.prototype_step_by_step.Model.Location;
import com.example.madisonmaddox.prototype_step_by_step.Model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by acrimin on 11/28/2016.
 */

class TaskDatabase {
    private String TASK_PHP = "https://people.cs.clemson.edu/~acrimin/getTasks.php/";

    public void update(SQLiteDatabase db, int user_id, String updated) throws ExecutionException, InterruptedException, JSONException {
        String results = "";
        String taskUrl = TASK_PHP + "?user_id=" + user_id + "&updated=" + updated;
        Log.i("Database SQL", "TaskPage: " + taskUrl);

        results = new PHPAccess().execute(taskUrl).get();
        Log.i("Database JSON", "TaskPage: " + results);
        JSONArray array = new JSONArray(results);

        db.execSQL("CREATE TABLE IF NOT EXISTS task (" +
                "task_id INTEGER(11) PRIMARY KEY, " +
                "name VARCHAR, " +
                "cover_image_filename VARCHAR)");


        int task_id;
        String name;
        String cover_image_filename;
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            task_id = object.getInt("task_id");
            name = object.getString("name");
            cover_image_filename = object.getString("cover_image_filename");

            String sql = "INSERT INTO task VALUES(" +
                    task_id + ", " +
                    "'" + name + "', " +
                    "'" + cover_image_filename + "')";

            db.execSQL(sql);
        }
    }

    public Task[] getAllTasks(SQLiteDatabase db) {
        String sql = "SELECT * FROM task";

        Cursor c = db.rawQuery(sql, null);
        ArrayList<Task> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                list.add(new Task(c.getInt(0), c.getString(1), c.getString(2)));
            } while (c.moveToNext());
        }

        Task[] tasks = new Task[list.size()];
        tasks = list.toArray(tasks);
        return tasks;
    }
}
