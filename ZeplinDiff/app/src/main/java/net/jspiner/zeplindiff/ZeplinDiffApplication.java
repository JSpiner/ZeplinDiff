package net.jspiner.zeplindiff;

import android.app.Application;

public class ZeplinDiffApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

    }

    public static Application getInstance(){
        return application;
    }
}
