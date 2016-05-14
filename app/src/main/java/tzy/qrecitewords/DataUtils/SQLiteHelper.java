package tzy.qrecitewords.dataUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.dataUtils.words_utils.TableInfo;
import tzy.qrecitewords.javabean.Library;

/**
 * Created by tzy on 2016/1/8.
 */
public class SQLiteHelper extends SQLiteOpenHelper{

    public static final int version = 1;

    public static final String dataBaseName = "qword.db3";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        creatTable_Library(db);
        createTable_Sentence(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**创建词库的表
     * @param tableName 词库名称
     * */
    public static void createTable_Word(String tableName,SQLiteDatabase db){
        StringBuilder sql = new StringBuilder("create table (");
        sql.append(tableName);
        sql.append( "_id integer primary key autoincrement,");
        sql.append(TableInfo.Table_Word.column_word + " text,");
        sql.append(TableInfo.Table_Word.column_phonogram + " text,");
        sql.append(TableInfo.Table_Word.column_paraphrase + " text,");
        sql.append(TableInfo.Table_Word.column_familiarity + " integer,");
        sql.append(TableInfo.Table_Word.column_lastReadTime + " Date,");
        sql.append(");");
    }

    /**创建词库信息表*/
    public static void creatTable_Library(SQLiteDatabase db){
        StringBuilder sql = new StringBuilder("create table (");
        sql.append(TableInfo.Table_Library.TABLE_NAME);
        sql.append( "_id integer primary key autoincrement,");
        sql.append(TableInfo.Table_Library.column_libraryName + " text,");
        sql.append(TableInfo.Table_Library.column_isExist + " integer,");
        sql.append(TableInfo.Table_Library.column_createdTime + " date,");
        sql.append(");");
        db.execSQL(sql.toString());
    }

    public static void createTable_Sentence(SQLiteDatabase db){
        StringBuilder sql = new StringBuilder("create table (");
        sql.append(TableInfo.Table_Sentence.TABLE_NAME);
        sql.append( "_id integer primary key autoincrement,");
        sql.append(TableInfo.Table_Sentence.column_content + " text,");
        sql.append(TableInfo.Table_Sentence.column_date + " date,");
        sql.append(");");
        db.execSQL(sql.toString());
    }

    public static boolean TransactionSubmit(ITransaction transaction){
        Boolean trTag = false;
        DatabaseWrapper wrapper = FlowManager.getDatabase(WordDataBase.class).getWritableDatabase();
        try {
            wrapper.beginTransaction();
            transaction.execute(wrapper);
            wrapper.setTransactionSuccessful();
            trTag = true;
        }catch (Exception e){
            trTag = false;
        }
        finally {
            wrapper.endTransaction();
        }

        return trTag;
    }

}
