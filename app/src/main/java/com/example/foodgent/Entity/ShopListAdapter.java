package com.example.foodgent.Entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.R;

import java.util.ArrayList;



public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder> {
    private ArrayList<ShoppingEntry> shoppingEntries;

    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopp_item, parent, false);
        ShopListViewHolder evh = new ShopListViewHolder(v);
        return evh;
    }

    public ShopListAdapter(ArrayList<ShoppingEntry> shoppingEntries) {
        this.shoppingEntries = shoppingEntries;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListViewHolder holder, int position) {
        ShoppingEntry currentItem = shoppingEntries.get(position);
        holder.mText.setText(currentItem.toString());
    }

    @Override
    public int getItemCount() {
        return shoppingEntries.size();
    }

    public static class ShopListViewHolder extends RecyclerView.ViewHolder{
        public TextView mText;

        public ShopListViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.textView_ItemName);
        }
    }
}

























