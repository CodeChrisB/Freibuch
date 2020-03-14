package com.example.foodgent.Logic;

import android.annotation.SuppressLint;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.foodgent.UserInterface.Fragment1;
import com.example.foodgent.UserInterface.Fragment2;
import com.example.foodgent.UserInterface.Fragment3;
import com.example.foodgent.UserInterface.MainActivity;
import com.example.fragment.R;

public class FragmentChanger extends AppCompatActivity {

    private Button btnNavFrag1;
    private Button btnNavFrag2;
    private Button btnNavFrag3;
    private MainActivity mainActivity;
    private int currentPage;

    public FragmentChanger(Button btnNavFrag1, Button btnNavFrag2, Button btnNavFrag3, MainActivity activity) {
        this.btnNavFrag1 = btnNavFrag1;
        this.btnNavFrag2 = btnNavFrag2;
        this.btnNavFrag3 = btnNavFrag3;
        this.mainActivity = activity;
        currentPage=0;
    }



    @SuppressLint("RestrictedApi")
    public void change(int number, ViewPager viewPager){

        final FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
        Fragment1 frag0 = new Fragment1();
        Fragment2 frag1 = new Fragment2();
        Fragment3 frag2 = new Fragment3();


        switch (number) {
            case 0:
                if (currentPage != 0) {
                    btnNavFrag1.setBackgroundResource(R.drawable.fragment_active_button);
                    btnNavFrag2.setBackgroundResource(R.drawable.fragment_buttons);
                    btnNavFrag3.setBackgroundResource(R.drawable.fragment_buttons);
                    Fragment1.setUpItemListView();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed
                    transaction.remove(frag1)
                            .remove(frag2)
                            .replace(R.id.fragment, new Fragment());
                }
                break;

            case 1:
                if (currentPage != 1) {
                    btnNavFrag1.setBackgroundResource(R.drawable.fragment_buttons);
                    btnNavFrag2.setBackgroundResource(R.drawable.fragment_active_button);
                    btnNavFrag3.setBackgroundResource(R.drawable.fragment_buttons);
                    Fragment1.setUpItemListView();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed
                    transaction.remove(frag0)
                            .remove(frag2)
                            .replace(R.id.fragment, new Fragment2());
                }
                break;
            case 2:

                if (currentPage != 2) {
                    btnNavFrag1.setBackgroundResource(R.drawable.fragment_buttons);
                    btnNavFrag2.setBackgroundResource(R.drawable.fragment_buttons);
                    btnNavFrag3.setBackgroundResource(R.drawable.fragment_active_button);
                    Fragment1.setUpItemListView();


                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed
                    transaction.replace(R.id.fragment, new Fragment3());

                }

                break;

        }

        transaction.addToBackStack(null);
        if (!this.isFinishing()) {
            transaction.commit();
        }

        currentPage=number;
        //set the new Page
        viewPager.setCurrentItem(number);
    }

    public int getCurrentPage(){
        return currentPage;
    }
}