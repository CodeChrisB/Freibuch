package com.foodgent.buchfrei.Logic;

import android.annotation.SuppressLint;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.fragment.R;
import com.foodgent.buchfrei.Gesture.GestureArea;
import com.foodgent.buchfrei.UserInterface.Item.ItemActivity;
import com.foodgent.buchfrei.UserInterface.MainActivity;
import com.foodgent.buchfrei.UserInterface.Recipe.RecipeActivity;
import com.foodgent.buchfrei.UserInterface.Shopping.ShopActivity;

public class FragmentChanger extends AppCompatActivity {

    private Button btnNavFrag1;
    private Button btnNavFrag2;
    private Button btnNavFrag3;
    private GestureArea gestureArea;
    private MainActivity mainActivity;
    private int currentPage;
    public static FragmentChanger fragmentChanger;
    private ViewPager mViewPager;

    public FragmentChanger(Button btnNavFrag1, Button btnNavFrag2, Button btnNavFrag3, MainActivity activity, ViewPager mViewPager) {
        this.btnNavFrag1 = btnNavFrag1;
        this.btnNavFrag2 = btnNavFrag2;
        this.btnNavFrag3 = btnNavFrag3;
        this.mainActivity = activity;
        currentPage=0;
        fragmentChanger = this;
        this.mViewPager = mViewPager;
        gestureArea = new GestureArea();
    }


    @SuppressLint("RestrictedApi")
    public void change(int number, ViewPager viewPager){

        final FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
        ItemActivity frag0 = new ItemActivity();
        RecipeActivity frag1 = new RecipeActivity();
        ShopActivity frag2 = new ShopActivity();


        switch (number) {
            case 0:

                ItemActivity.setUpItemListView();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed
                    transaction.remove(frag1)
                            .remove(frag2)
                            .replace(R.id.fragment, new Fragment());
                break;

            case 1:
                if (currentPage != 1) {
                    ItemActivity.setUpItemListView();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed
                    transaction.remove(frag0)
                            .remove(frag2)
                            .replace(R.id.fragment, new RecipeActivity());
                }
                break;
            case 2:

                if (currentPage != 2) {
                    ItemActivity.setUpItemListView();


                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed
                    transaction.replace(R.id.fragment, new ShopActivity());

                }

                break;

        }

        transaction.addToBackStack(null);

        transaction.commit();


        currentPage=number;
        CallGestureArea(currentPage);
        //set the new Page
        viewPager.setCurrentItem(number);
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public void nextPage() {
        if (currentPage <= 2) {
            this.change((currentPage + 1), MainActivity.getmViewPager());
            CallGestureArea(currentPage + 1);
        } else {
            this.change((0), MainActivity.getmViewPager());
            CallGestureArea(0);
        }
    }

    public void prevPage() {
        if (currentPage >= 1) {
            this.change((currentPage - 1), MainActivity.getmViewPager());
            CallGestureArea(currentPage - 1);
        } else {
            this.change((2), MainActivity.getmViewPager());
            CallGestureArea(2);
        }
    }

    private void CallGestureArea(int page) {
        //gestureArea.setArea(page);
    }
}