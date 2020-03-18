package com.example.foodgent.Entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.Item;
import com.example.fragment.R;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    private ArrayList<Item> items;

    @NonNull
    @Override
    public ItemListAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_itemconstraint, parent, false);
        ItemListAdapter.ItemListViewHolder evh = new ItemListAdapter.ItemListViewHolder(v);
        return evh;
    }

    public ItemListAdapter(ArrayList<Item> list) {
        this.items = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ItemListViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.mText.setText(currentItem.getName());
        holder.mAmount.setText(currentItem.toString());
        holder.date.setText(currentItem.getDateString());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder{
        public TextView mText;
        public TextView mAmount;
        public TextView date;
        public ItemListViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.checkBox_item);
            mAmount = itemView.findViewById(R.id.textView_Amount);
            date = itemView.findViewById(R.id.textView_item);
        }
    }
}
