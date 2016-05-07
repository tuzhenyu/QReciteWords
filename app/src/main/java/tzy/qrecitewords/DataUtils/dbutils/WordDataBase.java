package tzy.qrecitewords.dataUtils.dbutils;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by tzy on 2016/4/8.
 */
@Database(name = WordDataBase.NAME,version = WordDataBase.VERSION,foreignKeysSupported = true)
public class WordDataBase {
    //数据库名称
    public static final String NAME = "AppDatabase";
    //数据库版本号
    public static final int VERSION = 4;
}