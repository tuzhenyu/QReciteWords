package tzy.qrecitewords.javabean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;

/**
 * Created by tzy on 2016/5/9.
 */
@Table(database = WordDataBase.class)
public class MissionOfDay extends BaseModel {

    @PrimaryKey(autoincrement = true)
    Long id;

    @Column
    @Unique
    @NotNull
    Date date;

    @Column
    int todayWords;//今日要学的单词的数量

    @Column
    int countOfLearned;//今日已学的单词的数量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTodayWords() {
        return todayWords;
    }

    public void setTodayWords(int todayWords) {
        this.todayWords = todayWords;
    }

    public int getCountOfLearned() {
        return countOfLearned;
    }

    public void setCountOfLearned(int countOfLearned) {
        this.countOfLearned = countOfLearned;
    }
}
