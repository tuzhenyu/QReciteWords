package tzy.qrecitewords.dataUtils.serivce;

import android.database.Cursor;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.sql.Date;

import tzy.qrecitewords.javabean.Sentence;
import tzy.qrecitewords.javabean.Sentence_Adapter;
import tzy.qrecitewords.javabean.Sentence_Table;

/**
 * Created by tzy on 2016/5/15.
 */
public class SentenceService {

   public static Cursor getSentenceCursor(Date date){
       if(date == null){
           date = new Date(System.currentTimeMillis());
       }
        Cursor cursor = new Select().from(Sentence.class)
                         .where(Sentence_Table.date.eq(date.toString()))
                         .limit(1)
                         .query();
       if(cursor.getCount() == 0){
           cursor = new Select()
                   .from(Sentence.class)
                   .limit(1)
                   .orderBy(Sentence_Table.date,false)
                   .query();
           return cursor;
       }
       return cursor;
    }

    public static Sentence  getSentence(Date date){
        Cursor cursor = getSentenceCursor(date);
        Sentence_Adapter adapter = (Sentence_Adapter) FlowManager.getModelAdapter(Sentence.class);
        if(cursor.moveToNext()){
            return adapter.loadFromCursor(cursor);
        }else{
            return null;
        }

    }

    public static void  clearSentence(Transaction.Success success, Transaction.Error error){
        Sentence sentence = getSentence(new Date(System.currentTimeMillis()));
        if(sentence == null){
            success.onSuccess(null);
            return;
        }
        SQLite.delete(Sentence.class)
                .where(Sentence_Table._id.notEq(sentence.get_id()))
                .async()
                .error(error)
                .success(success)
                .execute();
    }

}
