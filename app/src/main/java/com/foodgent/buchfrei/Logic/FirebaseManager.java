package com.foodgent.buchfrei.Logic;


public class FirebaseManager {


    public void sendErrorReport(String message, String cause, String stacktrace, String codeLine) {
        String breakLine = "\n*****************************\n";
        String text = message + breakLine + cause + breakLine + stacktrace;
    }
}
