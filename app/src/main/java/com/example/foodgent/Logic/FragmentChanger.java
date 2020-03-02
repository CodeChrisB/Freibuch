package com.example.foodgent.Logic;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.foodgent.R;
import com.example.foodgent.UserInterface.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragmentChanger<mainActivity> {

    private Button btnNavFrag1;
    private Button btnNavFrag2;
    private Button btnNavFrag3;
    private TextView topbarName;
    private MainActivity mainActivity;
    FloatingActionButton fab;
    private int currentPage;



    public FragmentChanger(Button btnNavFrag1, Button btnNavFrag2, Button btnNavFrag3, TextView topbarName, MainActivity activity,FloatingActionButton fab) {
        this.btnNavFrag1 = btnNavFrag1;
        this.btnNavFrag2 = btnNavFrag2;
        this.btnNavFrag3 = btnNavFrag3;
        this.topbarName = topbarName;
        this.mainActivity= mainActivity;
        this.fab = fab;
        currentPage=0;
    }



    @SuppressLint("RestrictedApi")
    public void change(int number, ViewPager viewPager){
        switch (number){
            case 0:

                btnNavFrag1.setBackgroundResource(R.drawable.fragment_active_button);
                btnNavFrag2.setBackgroundResource(R.drawable.fragment_buttons);
                btnNavFrag3.setBackgroundResource(R.drawable.fragment_buttons);
                fab.setVisibility(View.INVISIBLE);

                topbarName.setText(R.string.fragment1_name);
            break;

            case 1:
                btnNavFrag1.setBackgroundResource(R.drawable.fragment_buttons);
                btnNavFrag2.setBackgroundResource(R.drawable.fragment_active_button);
                btnNavFrag3.setBackgroundResource(R.drawable.fragment_buttons);
                topbarName.setText(R.string.fragment2_name);

                fab.setVisibility(View.INVISIBLE);
                break;
            case 2:
                btnNavFrag1.setBackgroundResource(R.drawable.fragment_buttons);
                btnNavFrag2.setBackgroundResource(R.drawable.fragment_buttons);
                btnNavFrag3.setBackgroundResource(R.drawable.fragment_active_button);
                topbarName.setText(R.string.fragment3_name);

                fab.setVisibility(View.VISIBLE);
                break;

                default:
                    return;
        }
        currentPage=number;
        //set the new Page
        viewPager.setCurrentItem(number);
    }

    public int getCurrentPage(){
        return currentPage;
    }
}
