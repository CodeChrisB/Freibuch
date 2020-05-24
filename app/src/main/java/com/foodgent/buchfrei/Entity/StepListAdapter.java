package com.foodgent.buchfrei.Entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;

import java.util.ArrayList;
import java.util.List;


public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ShopListViewHolder> {
    private static boolean darkMode = AppData.getInstance().isDarkMode();
    private List<String> recipeSteps;


    public StepListAdapter(ArrayList<String> shoppingEntries) {
        this.recipeSteps = shoppingEntries;
    }

    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step_list, parent, false);
        ShopListViewHolder evh = new ShopListViewHolder(v);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopListViewHolder holder, int position) {
        final String currentStep = recipeSteps.get(position);
        String pos = (position + 1) + "";
        holder.step.setText(pos);
        holder.desc.setText(currentStep);
    }

    @Override
    public int getItemCount() {
        if (recipeSteps == null)
            return -1;
        return recipeSteps.size();
    }

    public static class ShopListViewHolder extends RecyclerView.ViewHolder {
        public TextView step;
        public TextView desc;


        public ShopListViewHolder(View itemView) {
            super(itemView);
            step = itemView.findViewById(R.id.textView_step);
            desc = itemView.findViewById(R.id.textView_stepDesc);
        }
    }
}

























