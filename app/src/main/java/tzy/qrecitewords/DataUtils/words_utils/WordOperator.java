package tzy.qrecitewords.dataUtils.words_utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import tzy.qrecitewords.javabean.LibraryInfo;
import tzy.qrecitewords.javabean.Word;

/**
 * 词库操作
 * Created by tzy on 2016/5/1.
 */
public class WordOperator extends DataBaseOperator {

    public static final String TAG = "WordOperator";

    public WordOperator(SQLiteDatabase database) {
        super(database);
    }

    public long insert(Word word){
        ContentValues values = new ContentValues();
        values.put(TableInfo.Table_Word.column_familiarity, LibraryInfo.Familiarity.noRead);
        values.put(TableInfo.Table_Word.column_lastReadTime,0);
        values.put(TableInfo.Table_Word.column_word,word.getWord());
        values.put(TableInfo.Table_Word.column_phonogram,word.getPhonogram());
        values.put(TableInfo.Table_Word.column_paraphrase,word.getParaphrase());
        return mDatabase.insert(TableInfo.Table_Word.TABLE_NAME,null,values);
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

    /**
     * 获取某个词库的单词的单词列表
     * @param tableName 词库名称
     * @param familarity 熟悉程度
     * @param count 查询数量
     * */
    public List<Word> getWordList(String tableName,int familarity,int count){
        if(checkDataBase((mDatabase))){return null;}
        StringBuilder query = new StringBuilder("select * ");
        query.append(" from ");
        query.append(tableName);
        query.append(" limit " + count);
        query.append(" where ");
        query.append(TableInfo.Table_Word.column_familiarity + " = ?");
        try{
           Cursor cursor = mDatabase.rawQuery(query.toString(), new String[]{String.valueOf(familarity)});
            List<Word> words = null;
        }catch(SQLException e){

        }
        return null;
    }

}
