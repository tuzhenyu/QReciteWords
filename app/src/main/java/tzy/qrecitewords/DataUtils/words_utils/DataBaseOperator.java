package tzy.qrecitewords.dataUtils.words_utils;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tzy on 2016/5/1.
 */
public class DataBaseOperator {

    SQLiteDatabase mDatabase;

    public DataBaseOperator(SQLiteDatabase database){
        this.mDatabase = database;
    }

}
