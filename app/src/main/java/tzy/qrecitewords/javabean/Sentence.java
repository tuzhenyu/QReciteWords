package tzy.qrecitewords.javabean;

import android.database.Cursor;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;


/**
 * Created by tzy on 2016/5/3.
 */
@Table(database = WordDataBase.class)
public class Sentence extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public Long _id;

    @Column
    public String content;

    @Column
    @Unique
    @NotNull
    String date;//yyyy-mm-dd

    public Sentence() {
    }

    public Long get_id() {
        return _id;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Sentence converto(Cursor cursor){

        Sentence sentence = new Sentence();
        sentence.setContent(cursor.getString(1));
        sentence.setDate(cursor.getString(2));
        return sentence;
    }
}
