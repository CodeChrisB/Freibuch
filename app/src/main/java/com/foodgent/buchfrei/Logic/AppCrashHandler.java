package com.foodgent.buchfrei.Logic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.UserInterface.Crashreport.CrashReport;
import com.foodgent.buchfrei.UserInterface.MainActivity;

import java.util.Arrays;

public class AppCrashHandler extends AppCompatActivity implements Thread.UncaughtExceptionHandler {
    Activity activity;

    public AppCrashHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {

        Intent intent = new Intent(activity, CrashReport.class);

        String message = ex.getMessage();

        String cause = ex.getCause() != null ? ex.getCause().toString() : "N/A";

        String stackTrace = Arrays.toString(ex.getStackTrace());

        String codeline = ex.getStackTrace()[0].toString();

        intent.putExtra("codeline", codeline + "");
        intent.putExtra("message", message);
        intent.putExtra("cause", cause);
        intent.putExtra("stacktracke", stackTrace);

        activity.startActivity(intent);
        activity.finish();

        System.exit(0);
    }

    private void handleError() {

        final AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.getInstance());
        final View deleteView = getLayoutInflater().inflate(R.layout.dialog_alert_error, null);

        helpDialog.setView(deleteView);
        final AlertDialog help = helpDialog.create();

        Button sendErrorText = deleteView.findViewById(R.id.error_sendError);
        sendErrorText.bringToFront();


        final Button helpNext = deleteView.findViewById(R.id.button_deleteNext);
        helpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseManager fm = new FirebaseManager();
                fm.sendErrorReport(AppData.getInstance().getMessage(), "", AppData.getInstance().getStacktrace(), getIntent().getStringExtra("codeline"));
            }
        });


        final Button buttonStop = deleteView.findViewById(R.id.button_deleteStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help.cancel();
            }
        });

        help.show();
    }


}
