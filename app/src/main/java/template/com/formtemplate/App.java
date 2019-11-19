package template.com.formtemplate;

import android.app.Application;

import template.com.templatedb.DaoManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DaoManager.getInstance().init(this);
    }
}
