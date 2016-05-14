package tzy.qrecitewords.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.MissionOfDay;
import tzy.qrecitewords.javabean.Sentence;
import tzy.qrecitewords.javabean.Word;

/**
 * Created by tzy on 2016/2/20.
 */
public class WordsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
       // FlowManager.getContext().deleteDatabase(WordDataBase.NAME + ".db");
     /*   ModelAdapter myAdapter1 = FlowManager.getModelAdapter(MissionOfDay.class);
        FlowManager.getDatabase(WordDataBase.class).getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + myAdapter1.getTableName());
        FlowManager.getDatabase(WordDataBase.class).getWritableDatabase().execSQL(myAdapter1.getCreationQuery());

        ModelAdapter myAdapter2 = FlowManager.getModelAdapter(Sentence.class);
        FlowManager.getDatabase(WordDataBase.class).getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + myAdapter2.getTableName());
        FlowManager.getDatabase(WordDataBase.class).getWritableDatabase().execSQL(myAdapter2.getCreationQuery());*/
    }
}
