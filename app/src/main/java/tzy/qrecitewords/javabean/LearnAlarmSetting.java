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
public class LearnAlarmSetting extends BaseModel {

    @PrimaryKey(autoincrement = true)
    Long _id;

    @Column(defaultValue = "20")
    int hour;

    @Column(defaultValue = "30")
    int minute;

    @Column(defaultValue = "true")
    boolean isOpen;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
