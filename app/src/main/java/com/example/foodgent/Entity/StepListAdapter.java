package com.example.foodgent.Entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.RecipeStep;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.fragment.R;

import java.util.ArrayList;
import java.util.List;


public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ShopListViewHolder> {
    private static boolean darkMode = AppData.getInstance().isDarkMode();
    private List<RecipeStep> shoppingEntries;


    public StepListAdapter(ArrayList<RecipeStep> shoppingEntries) {
        this.shoppingEntries = shoppingEntries;
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
        final RecipeStep currentItem = shoppingEntries.get(position);
        holder.step.setText(currentItem.stepToString());
        holder.desc.setText(currentItem.getStepDescription());
    }


    @Override
    public int getItemCount() {
        if (shoppingEntries == null)
            return -1;
        return shoppingEntries.size();
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

























