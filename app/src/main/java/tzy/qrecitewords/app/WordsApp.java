package tzy.qrecitewords.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.Word;

/**
 * Created by tzy on 2016/2/20.
 */
public class WordsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
