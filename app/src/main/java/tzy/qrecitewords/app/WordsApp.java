package tzy.qrecitewords.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by tzy on 2016/2/20.
 */
public class WordsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
