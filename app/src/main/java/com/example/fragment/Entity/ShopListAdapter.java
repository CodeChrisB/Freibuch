package com.example.fragment.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fragment.AppData.MainLists.ShoppingEntries;
import com.example.fragment.R;

import java.util.ArrayList;



public class ShopListAdapter extends ArrayAdapter<ShoppingEntries> {

    private static final String TAG = "ShopListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        CheckBox checkBox;
    }

    public ShopListAdapter(Context context, int resource, ArrayList<ShoppingEntries> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public void add(@Nullable ShoppingEntries object) {
        super.add(object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the shop list information
        String name = getItem(position).getName();


        //Create the shopEntry object with the information
        ShoppingEntries shopEntry = new ShoppingEntries(name);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView_feedbackName);
            holder.checkBox = convertView.findViewById(R.id.checkBox_shopEntry);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }




        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(shopEntry.getName());

        return convertView;
    }
}

























