package com.example.madisonmaddox.prototype_step_by_step.Model;

/**
 * Created by acrimin on 11/28/2016.
 */

public class Step {
    private int stepNum;
    private String stepInfo;
    private String imageFilename;

    public Step(int stepNum, String stepInfo, String imageFilename) {
        this.stepNum = stepNum;
        this.stepInfo = stepInfo;
        this.imageFilename = imageFilename;
    }

    public int getStepNum() {
        return stepNum;
    }

    public String getStepInfo() {
        return stepInfo;
    }

    public String getImageFilename() {
        return imageFilename;
    }


    @Override
    public String toString() {
        return "Step{" +
                "stepNum=" + stepNum +
                ", stepInfo='" + stepInfo + '\'' +
                '}';
    }
}
