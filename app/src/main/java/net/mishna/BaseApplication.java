package net.mishna;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class BaseApplication extends Application {
    private static Context context;
    private static Resources appRes;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        context = getApplicationContext();
        appRes = getResources();
    }

    /**
     * @return the context
     */
    public static Context getContext() {
        return context;
    }


    /**
     * @return the appRes
     */
    public static Resources getAppRes() {
        return appRes;
    }

}
