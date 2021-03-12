package com.prismhospitalities.utils;

import android.app.Application;
import com.onesignal.OneSignal;

public class AppApplication extends Application {
    private static final String ONESIGNAL_APP_ID = "f19db43f-2f04-4c16-acb0-0fb1e933b6e4";

    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtils.getInstance().setPrefContext(getApplicationContext());
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
