package com.example.foodgent.Entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.fragment.R;

import java.util.ArrayList;
import java.util.List;


public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder> {
    private List<ShoppingEntry> shoppingEntries;

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
    public void onBindViewHolder(@NonNull final ShopListViewHolder holder, int position) {
        final ShoppingEntry currentItem = shoppingEntries.get(position);
        holder.mText.setText(currentItem.toString());
        holder.mcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentItem.setSelected(isChecked);
                AppData.getInstance().saveShopEntries();
            }

        });
        holder.mcheckBox.setChecked(currentItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return shoppingEntries.size();
    }

    public static class ShopListViewHolder extends RecyclerView.ViewHolder{
        public TextView mText;
        public CheckBox mcheckBox;


        public ShopListViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.textView_ItemName);
            mcheckBox = itemView.findViewById(R.id.checkBox_shopEntry);
        }
    }
}

























