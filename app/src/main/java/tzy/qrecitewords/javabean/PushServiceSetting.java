package tzy.qrecitewords.javabean;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;

/**
 * Created by tzy on 2016/5/17.
 */
@Table(database = WordDataBase.class)
public class PushServiceSetting extends BaseModel {

    @PrimaryKey(autoincrement = true)
    Long _id;

    boolean isNotifyPush;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
