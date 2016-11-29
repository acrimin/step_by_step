package com.example.madisonmaddox.prototype_step_by_step.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.madisonmaddox.prototype_step_by_step.Model.Location;
import com.example.madisonmaddox.prototype_step_by_step.Model.Step;
import com.example.madisonmaddox.prototype_step_by_step.Model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by acrimin on 11/28/2016.
 */

class TaskStepsDatabase {

    private String TASK_STEPS_PHP = "https://people.cs.clemson.edu/~acrimin/getTaskSteps.php/";

    public void update(SQLiteDatabase db, int user_id, String updated) throws ExecutionException, InterruptedException, JSONException {
        String results = "";
        String taskUrl = TASK_STEPS_PHP + "?user_id=" + user_id + "&updated=" + updated;

        results = new PHPAccess().execute(taskUrl).get();
        Log.i("Database JSON", "Steps: " + results);
        JSONArray array = new JSONArray(results);

        db.execSQL("CREATE TABLE IF NOT EXISTS task_steps (" +
                "task_id INTEGER(11), " +
                "step_num INTEGER(11), " +
                "step_info VARCHAR," +
                "image_filename VARCHAR)");

        int task_id;
        int step_num;
        String step_info;
        String image_filename;
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            task_id = object.getInt("task_id");
            step_num = object.getInt("step_num");
            step_info = object.getString("step_info");
            image_filename = object.getString("image_filename");

            String sql = "INSERT INTO task_steps VALUES(" +
                    task_id + ", " +
                    step_num + ", " +
                    "'" + step_info + "', " +
                    "'" + image_filename + "')";

            db.execSQL(sql);
        }
    }

    /* Returns steps in order */
    public Step[] getSteps(SQLiteDatabase db, Task task) {
        String sql = "SELECT step_num, step_info, image_filename FROM task_steps WHERE task_id = ?";
        String[] args = new String[1];
        args[0] = String.valueOf(task.getId());

        Cursor c = db.rawQuery(sql, args);

        ArrayList<Step> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                list.add(new Step(c.getInt(0), c.getString(1), c.getString(2)));
            } while (c.moveToNext());
        }

        Step[] steps = new Step[list.size()];
        steps = list.toArray(steps);
        return steps;
    }

}
