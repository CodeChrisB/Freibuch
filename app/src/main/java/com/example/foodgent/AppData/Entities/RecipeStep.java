package com.example.foodgent.AppData.Entities;

public class RecipeStep {
    int step;
    String stepDescription;

    public RecipeStep(int step, String stepDescription) {
        this.step = step;
        this.stepDescription = stepDescription;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
}
