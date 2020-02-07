package com.example.fragment.UserInterface;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragment.R;

public class Fragment1 extends Fragment {

    private static final String TAG = "Fragment1";

    private Button btnNavFrag1;
    private Button btnNavFrag2;
    private Button btnNavFrag3;
    private Button btnNavSecondActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);

            btnNavFrag1 = (Button) view.findViewById(R.id.btnNavFrag1);
            btnNavFrag2 = (Button) view.findViewById(R.id.btnNavFrag2);
            btnNavFrag3 = (Button) view.findViewById(R.id.btnNavFrag3);
            btnNavSecondActivity = (Button) view.findViewById(R.id.btnNavSecondActivity);
            Log.d(TAG, "onCreateView: started");


            btnNavSecondActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Going 2nd Activity", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), SecondActivity.class);
                    startActivity(intent);
                }
            });



            return view;

    }
}
