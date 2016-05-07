package tzy.qrecitewords.javabean;


import android.database.Cursor;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;

/**
 * Created by tzy on 2016/1/7.
 */
@Table(database = WordDataBase.class, cachingEnabled = true)
public class Word extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public Long id;

    /**原单词*/
    @Column
    public String word;

    /**音标*/
    @Column
    public String phonogram;


    /**中文解释*/
    @Column
    public String paraphrase;

    /**熟悉程度 1代表熟悉；2代表不熟悉；3代表很不熟悉，0代表没读过*/
    @Column
    public int familiarity;

    /**最后阅读时间*/
    @Column
    public Long lastReadTime ;

    @ForeignKey
    public Library library;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getPhonogram() {
        return phonogram;
    }

    public void setPhonogram(String phonogram) {
        this.phonogram = phonogram;
    }

    public int getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(int familiarity) {
        this.familiarity = familiarity;
    }

    public long getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(long lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public static List<Word> convertToList(Cursor cursor){

        List<Word> words = new ArrayList<>(cursor.getCount());
        Word word = null;
        while(cursor.moveToNext()){
            word = new Word();
            word.setWord(cursor.getString(0));
            word.setPhonogram(cursor.getString(1));
            word.setParaphrase(cursor.getString(2));
            word.setFamiliarity(cursor.getInt(3));
            word.setLastReadTime(cursor.getLong(4));
            words.add(word);
        }
        return words;
    }

    public static Word conveToWord(Cursor cursor){

        Word word = new Word();
        word.setWord(cursor.getString(0));
        word.setPhonogram(cursor.getString(1));
        word.setParaphrase(cursor.getString(2));
        word.setFamiliarity(cursor.getInt(3));
        word.setLastReadTime(cursor.getLong(4));

        return word;
    }

}
