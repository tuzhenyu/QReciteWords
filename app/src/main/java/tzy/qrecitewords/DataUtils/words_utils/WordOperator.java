package tzy.qrecitewords.DataUtils.words_utils;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import tzy.qrecitewords.javabean.LibraryInfo;

/**
 * 词库操作
 * Created by tzy on 2016/5/1.
 */
public class WordOperator extends DataBaseOperator {

    public static final String TAG = "WordOperator";

    public WordOperator(SQLiteDatabase database) {
        super(database);
    }

    /**获取词库信息*/
    public LibraryInfo getLibraryInfo(String libraryName){
        if(checkDataBase((mDatabase))){return null;}

        StringBuilder query = new StringBuilder("select ");
        query.append(TableInfo.Table_Word.column_familiarity);
        query.append(",");
        query.append(" COUNT(*) ");
        query.append("from ");
        query.append(libraryName);
        query.append(" group by ");
        query.append(TableInfo.Table_Word.column_familiarity);
        query.append(";");
        Cursor cursor = null;
        try{
            cursor = mDatabase.rawQuery(query.toString(), null);
            LibraryInfo libraryInfo  = LibraryInfo.CursorToLibraryInfo(libraryName, cursor);
            return  libraryInfo;
        }catch(SQLException e){
            Log.e(TAG,e.toString());
            return null;
        }finally {
            cursor.close();
        }
    }

    public static boolean checkDataBase(SQLiteDatabase database){
        if(database == null || database.isOpen() == false){
            return false;
        }
        return true;
    }

}
