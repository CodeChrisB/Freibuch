package com.foodgent.buchfrei.Gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    Context context;

    public OnSwipeTouchListener(Context ctx, View mainView) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        mainView.setOnTouchListener(this);
        context = ctx;
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {

        simulateClick(e.getX(), e.getY());
        return gestureDetector.onTouchEvent(e);


    }

    private void simulateClick(float x, float y) {


    }

}
