package com.foodgent.buchfrei;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.ShoppingEntry;
import com.foodgent.buchfrei.AppData.MainLists.ShoppingEntries;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.UserInterface.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public final class WidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }

    public static final class WidgetItem {

        public final String mLabel;

        public WidgetItem(String mLabel) {
            this.mLabel = mLabel;
        }


    }

    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private final List<WidgetItem> mWidgetItems = new ArrayList<>();
        private final Context mContext;

        public ListRemoteViewsFactory(Context context) {
            mContext = context;
        }

        @Override
        public void onCreate() {
            Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(MainActivity.getInstance()));
        }

        @Override
        public void onDestroy() {
            mWidgetItems.clear();
        }

        @Override
        public int getCount() {
            return mWidgetItems.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            WidgetItem widgetItem = mWidgetItems.get(position);
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            rv.setTextViewText(R.id.widget_item, widgetItem.mLabel);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onDataSetChanged() {
            // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
            // on the collection view corresponding to this factory. You can do heaving lifting in
            // here, synchronously. For example, if you need to process an image, fetch something
            // from the network, etc., it is ok to do it here, synchronously. The widget will remain
            // in its current state while work is being done here, so you don't need to worry about
            // locking up the widget.
            mWidgetItems.clear();

            Gson gson = new Gson();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getInstance());
            String json = sharedPreferences.getString("shopping", "");
            ArrayList<ShoppingEntry> list = gson.fromJson(json, ShoppingEntries.class).getArray();
            for (int i = 0; i < list.size(); i++) {
                WidgetItem item = new WidgetItem(list.get(i).getName());
                mWidgetItems.add(item);
            }
        }
    }
}
