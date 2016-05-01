package tzy.qrecitewords.javabean;

/*import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;*/

import tzy.qrecitewords.DataUtils.dbutils.WordDataBase;

/**
 * Created by tzy on 2016/1/7.
 */

public class Word extends BaseData{

    /**原单词*/
   // @Column
    String word;

    /**音标*/
    //@Column
    String phonogram;

    /**中文解释*/
   // @Column
    String paraphrase;

    /**熟悉程度 1代表熟悉；2代表不熟悉；3代表很不熟悉，0代表没读过*/
    //@Column
    int familiarity;

    /**最后阅读时间*/
   // @Column
    long lastReadTime ;

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

}
