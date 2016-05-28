package tzy.qrecitewords.javabean;


import android.database.Cursor;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.NotNull;
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

    public static final String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @PrimaryKey(autoincrement = true)
    public Long _id;

    /**原单词*/
    @Column
    @NotNull
    public String word;

    /**音标*/
    @Column
    @NotNull
    public String phonogram;


    /**中文解释*/
    @Column
    @NotNull
    public String paraphrase;

    /**熟悉程度 1代表熟悉；2代表不熟悉；3代表很不熟悉，0代表没读过*/
    @Column(defaultValue = "0")
    public int familiarity = 0;

    @ForeignKey(saveForeignKeyModel = true)
    public Library library;

    @Column
    String lastReadDate;//yyyy-mm-dd

    public String getLastReadDate() {
        return lastReadDate;
    }

    public void setLastReadDate(String lastReadDate) {
        this.lastReadDate = lastReadDate;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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
            words.add(word);
        }
        return words;
    }

    public static Word conveToWord(Cursor cursor){

        Word word = new Word();
        word.set_id(cursor.getLong(0));
        word.setWord(cursor.getString(1));
        word.setPhonogram(cursor.getString(2));
        word.setParaphrase(cursor.getString(3));
        word.setFamiliarity(cursor.getInt(4));

        return word;
    }

    public static Word conveToWord(Word word,Cursor cursor){

        word.set_id(cursor.getLong(0));
        word.setWord(cursor.getString(1));
        word.setPhonogram(cursor.getString(2));
        word.setParaphrase(cursor.getString(3));
        word.setFamiliarity(cursor.getInt(4));
        return word;
    }
    public static int getPositionForSection(int sectionIndex){
        int letter = Character.toUpperCase(sectionIndex);
        switch(letter){
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
            case 'K':
                return 10;
            case 'L':
                return 11;
            case 'M':
                return 12;
            case 'N':
                return 13;
            case 'O':
                return 14;
            case 'P':
                return 15;
            case 'Q':
                return 16;
            case 'R':
                return 17;
            case 'S':
                return 18;
            case 'T':
                return 19;
            case 'U':
                return 20;
            case 'V':
                return 21;
            case 'W':
                return 22;
            case 'X':
                return 23;
            case 'Y':
                return 24;
            case 'Z':
                return 25;
        }
        return 25;
    }

    public boolean changeFamlility(int newFamility){
        int oldFamility = familiarity;
        if(newFamility == oldFamility){
            return false;
        }

        switch(newFamility){
            case Library.Familiarity.familary:
                familiarity = newFamility;
                return true;
            case Library.Familiarity.nofamilary:
                familiarity = newFamility;
                return true;
            case Library.Familiarity.noknown:
                familiarity = newFamility;
                return true;
            case Library.Familiarity.noRead:
                familiarity = newFamility;
                return true;
            default:
                throw new IllegalArgumentException("词组类型不能为未定义类型");
        }
    }



}
