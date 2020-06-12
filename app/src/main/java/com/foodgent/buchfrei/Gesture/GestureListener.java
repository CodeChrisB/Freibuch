package com.foodgent.buchfrei.Gesture;

import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Logic.FragmentChanger;
import com.foodgent.buchfrei.UserInterface.MainActivity;

public class GestureListener extends
        GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (diffX == 0 && diffY == 0)
                onTouch();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeLeft();
                    } else {
                        onSwipeRight();

                    }
                }
                result = true;
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
            }
            result = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    private void onTouch() {
        Toast.makeText(MainActivity.getInstance().getContext(), "Touch", Toast.LENGTH_SHORT).show();
    }

    private void onSwipeBottom() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void onSwipeTop() {
        //add page of current page
        if (!AppData.getInstance().getGesture().isUp())
            return;

        Toast.makeText(MainActivity.getInstance().getContext(), "Swipe Up", Toast.LENGTH_SHORT).show();

        int addPage = FragmentChanger.fragmentChanger.getCurrentPage();
        Intent intent = MainActivity.getInstance().getMainAction().getCorrectAddPage(addPage);

        MainActivity.getInstance().getMainAction().startAddIntent(intent);

    }

    void onSwipeRight() {
        if (AppData.getInstance().getGesture().isSide())
            MainActivity.getInstance().getMainAction().prevViewPager();
    }

    void onSwipeLeft() {
        if (AppData.getInstance().getGesture().isSide())
            MainActivity.getInstance().getMainAction().nextViewPager();
    }

}