package com.foodgent.buchfrei.Entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Logic.AppData;

import java.util.ArrayList;

public class RecipeListAdapter  extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>{
    private ArrayList<Recipe> items;

    @NonNull
    @Override
    public RecipeListAdapter.RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopp_item, parent, false);
        RecipeListAdapter.RecipeListViewHolder evh = new RecipeListAdapter.RecipeListViewHolder(v);
        return evh;
    }

    public RecipeListAdapter(ArrayList<Recipe> list) {
        this.items = list;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeListViewHolder holder, int position) {
        final Recipe currentItem = items.get(position);
        holder.mText.setText(currentItem.toString());
        holder.mcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentItem.setSelected(isChecked);
                AppData.getInstance().saveRecipe();
            }

        });
        holder.mcheckBox.setChecked(currentItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecipeListViewHolder extends RecyclerView.ViewHolder{
        public TextView mText;
        public CheckBox mcheckBox;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.textView_step);
        }
    }
}