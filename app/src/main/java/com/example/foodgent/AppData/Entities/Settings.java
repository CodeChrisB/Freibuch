package com.example.foodgent.AppData.Entities;

import java.io.Serializable;

public class Settings implements Serializable {

    private boolean useDarkmode;
    private boolean useBigText;
    private boolean sendNotification;

    public Settings() {
    }

    public boolean isUseDarkmode() {
        return useDarkmode;
    }

    public void setUseDarkmode(boolean useDarkmode) {
        this.useDarkmode = useDarkmode;
    }

    public boolean isUseBigText() {
        return useBigText;
    }

    public void setUseBigText(boolean useBigText) {
        this.useBigText = useBigText;
    }

    public boolean isSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(boolean sendNotification) {
        this.sendNotification = sendNotification;
    }
}
