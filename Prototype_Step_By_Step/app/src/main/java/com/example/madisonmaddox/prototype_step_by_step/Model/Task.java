package com.example.madisonmaddox.prototype_step_by_step.Model;

import com.example.madisonmaddox.prototype_step_by_step.Database.DatabaseManager;

/**
 * Created by acrimin on 11/28/2016.
 */

public class Task {
    final int id;
    final String name;
    final String imageFilename;

    public Task(int id, String name, String imageFilename) {
        this.id = id;
        this.name = name;
        this.imageFilename = imageFilename;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public Step[] getSteps() {
        return DatabaseManager.getInstance().getAllSteps(this);
    }

    @Override
    public String toString() {
        return "TaskPage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
