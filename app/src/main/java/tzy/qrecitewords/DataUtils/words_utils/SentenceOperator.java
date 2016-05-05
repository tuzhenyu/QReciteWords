package tzy.qrecitewords.dataUtils.words_utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;

import tzy.qrecitewords.javabean.Sentence;

/**
 * Created by tzy on 2016/5/4.
 */
public class SentenceOperator extends DataBaseOperator {
    public SentenceOperator(SQLiteDatabase database) {
        super(database);
    }

    public Sentence getTodaySentence(Date date){
        String defaultS ="to be or not to ne ,is a question.";
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(TableInfo.Table_Sentence.TABLE_NAME);
        sql.append(" where ");
        sql.append(TableInfo.Table_Sentence.column_date);
        sql.append("= ?");
        Cursor cursor = mDatabase.rawQuery(sql.toString(),new String[]{date.toString()});

        if(cursor.moveToNext()){
            Sentence sentence = Sentence.converto(cursor);
            return sentence;
        }

        StringBuffer sqls = new StringBuffer("select * from ");
        sql.append(TableInfo.Table_Sentence.TABLE_NAME);
        sql.append(" limit 1 ");
        sql.append("order by" + TableInfo.Table_Sentence.column_date + " desc ");
        cursor = mDatabase.rawQuery(sql.toString(),new String[]{date.toString()});
        if(cursor.moveToNext()){
            Sentence sentence = Sentence.converto(cursor);
            return sentence;
        }

        Sentence sentence = new Sentence();
        sentence.setContent(defaultS);
        return sentence;
    }
}
