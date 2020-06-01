package com.foodgent.buchfrei.Entity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.UserInterface.Recipe.ShowRecipeActivity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> mData;


    public RecyclerViewAdapter(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_recipe, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        int image;
        boolean isDarkMode = AppData.getInstance().isDarkMode();

        holder.recipeName.setText(mData.get(position).getName());
        if (mData.get(position).getRecipeType().equals("Fleisch")) {
            image = isDarkMode ? R.drawable.steak_dark : R.drawable.steak;
        } else if(mData.get(position).getRecipeType().equals("Vegetarisch")) {
            image = isDarkMode ? R.drawable.vegan_dark : R.drawable.vegan;
        } else if(mData.get(position).getRecipeType().equals("Vegan")) {
            image = isDarkMode ? R.drawable.veggie_dark : R.drawable.veggie;
        } else {
            image = isDarkMode ? R.drawable.cake_dark : R.drawable.cake;
        }


        holder.recipePicture.setImageDrawable(mContext.getResources().getDrawable(image));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ShowRecipeActivity.class);
                Gson gson = new Gson();

                String json = gson.toJson(mData.get(position));
                intent.putExtra("recipe", json);

                // passing data to the book activity
//                intent.putExtra("Title", mData.get(position).getTitle());
//                intent.putExtra("Description", mData.get(position).getDescription());
//                intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                // start the activity
                mContext.startActivity(intent);
                holder.cb.setChecked(mData.get(position).isSelected());

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        ImageView recipePicture;
        CardView cardView;
        CheckBox cb;

        public MyViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_checkBox_id);
            recipePicture = itemView.findViewById(R.id.recipe_pictureID);
            cardView = itemView.findViewById(R.id.cardview_id);
            cb = itemView.findViewById(R.id.recipe_checkBox_id);

        }
    }


}