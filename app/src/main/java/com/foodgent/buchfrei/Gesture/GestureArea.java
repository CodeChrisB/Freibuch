package com.foodgent.buchfrei.Gesture;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GestureArea extends AppCompatActivity {
    private int lastPage = 0;

    private View ItemDetectView;
    private View RecipeDetectView1;
    private View RecipeDetectView2;
    private View RecipeDetectView3;

    public GestureArea() {

    }

    public void setArea(int page) {

        //set new detection
        detection(page, true);

        //remove the last detection
        detection(lastPage, false);


        lastPage = page;
    }

    private void detection(int page, boolean state) {
        switch (page) {
            case 0:
                setItemDetection(state);
                break;
            case 1:
                setRecipeDetection(state);
                break;
            case 2:
                setShopDetection(state);
                break;
        }
    }


    private void setRecipeDetection(boolean set) {
        int state = set ? View.VISIBLE : View.GONE;

        RecipeDetectView1.setVisibility(state);
        RecipeDetectView2.setVisibility(state);
        RecipeDetectView3.setVisibility(state);
    }

    private void setShopDetection(boolean set) {
        int state = set ? View.VISIBLE : View.GONE;
    }

    private void setItemDetection(boolean set) {
        int state = set ? View.VISIBLE : View.GONE;
        ItemDetectView.setVisibility(state);
    }
}
