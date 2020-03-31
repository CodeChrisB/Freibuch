package com.example.foodgent.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;

import java.util.List;


public class RecipeItemListAdapter extends RecyclerView.Adapter<RecipeItemListAdapter.ShopListViewHolder> {
    private List<String> recipeItems;


    public RecipeItemListAdapter(List<String> recipeItems) {
        this.recipeItems = recipeItems;
    }

    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipeitem, parent, false);
        RecipeItemListAdapter.ShopListViewHolder evh = new RecipeItemListAdapter.ShopListViewHolder(v);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopListViewHolder holder, int position) {
        final String text = recipeItems.get(position);
        holder.mText.setText(text);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.context, "Jaja du hast auf " + text + " geklickt", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeItems.size();
    }


    public static class ShopListViewHolder extends RecyclerView.ViewHolder {
        public TextView mText;
        ConstraintLayout layout;
        Context context;

        public ShopListViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.textView_recipeItem);
            layout = itemView.findViewById(R.id.recipeItemLine);
            context = itemView.getContext();
        }
    }
}

























