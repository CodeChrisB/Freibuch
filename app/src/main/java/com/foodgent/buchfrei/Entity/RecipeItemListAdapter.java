package com.foodgent.buchfrei.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.UserInterface.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class RecipeItemListAdapter extends RecyclerView.Adapter<RecipeItemListAdapter.ShopListViewHolder> {
    boolean isOnClickActive;
    private ArrayList<String > recipeList = new ArrayList<>();
    private List<String> recipeItems;
    private static List<Integer> holderList;
    boolean change;
    private TextView textView;

    public RecipeItemListAdapter(List<String> recipeItems, boolean isOnClickActive) {
        this.recipeItems = recipeItems;
        this.isOnClickActive = isOnClickActive;
    }

    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipeitem, parent, false);
        RecipeItemListAdapter.ShopListViewHolder evh = new RecipeItemListAdapter.ShopListViewHolder(v);
        holderList = new ArrayList<>();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopListViewHolder holder, int position) {

        String[] info = recipeItems.get(position).split(":");

        //amout:name:unit
        final String text = info[1];
        holder.mText.setText(text);
        String amountText = info[0] + " " +
                AppData.getInstance().getUnitFromName(info[1]);

        holder.itemAmount.setText(amountText);

        if (change) {
            int amount = getAmountOfChange(holder.mText);
            if (amount != -1 && amount > 0)
                displayItemAmount(holder, amount);
        }


        if (isOnClickActive) {

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Open the Dialog to select the amount of an item

                    final androidx.appcompat.app.AlertDialog.Builder helpDialog = new androidx.appcompat.app.AlertDialog.Builder(holder.context);
                    final View recipeItemView = MainActivity.getInstance().getLayoutInflater().inflate(R.layout.alert_recipe_item_amount, null);
                    helpDialog.setView(recipeItemView);
                    final AlertDialog help = helpDialog.create();


                    textView = recipeItemView.findViewById(R.id.textView_rI_name);
                    textView.setText(text);


                    final EditText amountView = recipeItemView.findViewById(R.id.editText_recipeItemAmount);
                    Button setRecipeItemAmount = recipeItemView.findViewById(R.id.button_setRecipeItemAmount);


                    setRecipeItemAmount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //check if the amount is a correct input
                            int amount = -1;
                            try {
                                amount = Integer.parseInt(amountView.getText().toString());
                            } catch (Exception e) {
                                //nope wrong input set -1
                                amount = -1;
                            }

                            //if it was a correct input set the value, add to recipeItem list and set background
                            displayItemAmount(holder, amount);
                            help.cancel();
                        }
                    });
                    help.show();

                }
            });
        }

    }

    private int getAmountOfChange(TextView mText) {
        for (String recipe : recipeList) {
            String otherName = mText.getText().toString();
            String name = recipe.split(":")[1];
            if (name.equals(otherName))
                return Integer.parseInt(recipe.split(":")[0]);
        }
        return -1;
    }

    private void displayItemAmount(ShopListViewHolder holder, int amount) {
        if (amount > 0) {

            String amountText = amount + " ";
            holder.itemAmount.setText(amountText);
            if (!change) {
                recipeList.add(amount + ":" + textView.getText().toString() + ":" + "");
            }
            holder.background.setBackgroundTintList(holder.context.getResources().getColorStateList(R.color.themeColor));
        }

    }

    @Override
    public int getItemCount() {
        if (recipeItems == null)
            return -1;
        return recipeItems.size();
    }

    public ArrayList<String> getNeededRecipeItems() {
        return recipeList;
    }

    public void setNeededItems(ArrayList<String> listOfItems) {
        change = true;

        for (int i = 0; i < listOfItems.size(); i++) {
            String item = listOfItems.get(i);
            String name = item.split(":")[1];
            int amount = Integer.parseInt(item.split(":")[0]);
            item += AppData.getInstance().getUnitFromName(name);
            listOfItems.set(i, item);
        }


        recipeList.clear();
        recipeList.addAll(listOfItems);
    }

    public static class ShopListViewHolder extends RecyclerView.ViewHolder {
        public TextView mText;
        ConstraintLayout layout;
        Context context;
        TextView itemAmount;
        View background;


        public ShopListViewHolder(View itemView) {
            super(itemView);
            itemAmount = itemView.findViewById(R.id.textView_list_recipeAmount);
            mText = itemView.findViewById(R.id.textView_list_recipeItem);
            layout = itemView.findViewById(R.id.recipeItemLine);
            context = itemView.getContext();
            background = itemView.findViewById(R.id.view_recipeItemBg);
        }
    }
}

























