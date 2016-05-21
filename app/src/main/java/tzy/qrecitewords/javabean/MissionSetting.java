package tzy.qrecitewords.javabean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;

/**
 * Created by tzy on 2016/5/17.
 */
@Table(database = WordDataBase.class)
public class MissionSetting extends BaseModel {

    public static int[] DAYCOUNTs = {60,100,140,180,240};

    @PrimaryKey(autoincrement = true)
    Long _id;

    @Column(defaultValue = "60")
    int dayCount;//每日单词数

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public static int getCountFromStatic(int index){
        return DAYCOUNTs[index];
    }
}
