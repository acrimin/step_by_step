package com.example.madisonmaddox.prototype_step_by_step.Database;

import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by acrimin on 11/28/2016.
 */

class TaskVideoDatabase {
    private String TASK_VIDEO_PHP = "https://people.cs.clemson.edu/~acrimin/getTaskVideos.php/";

    public void update(SQLiteDatabase db, int user_id, String updated) throws ExecutionException, InterruptedException, JSONException {
        String results = "";
        String taskUrl = TASK_VIDEO_PHP + "?user_id=" + user_id + "&updated=" + updated;

        results = new PHPAccess().execute(taskUrl).get();
        JSONArray array = new JSONArray(results);

        db.execSQL("CREATE TABLE IF NOT EXISTS task_video (" +
                "task_video_id INTEGER(11) PRIMARY KEY, " +
                "task_id INTEGER(11), " +
                "thumbnail_filename VARCHAR, " +
                "video_filename VARCHAR)");

        int task_video_id;
        int task_id;
        String thumbnail_filename;
        String video_filename;
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            task_video_id = object.getInt("task_steps_id");
            task_id = object.getInt("task_id");
            thumbnail_filename = object.getString("thumbnail_filename");
            video_filename = object.getString("video_filename");

            String sql = "INSERT INTO task_video VALUES(" +
                    task_video_id + ", " +
                    task_id + ", " +
                    "'" + thumbnail_filename + "', " +
                    "'" + video_filename + "')";

            db.execSQL(sql);
        }
    }
}
