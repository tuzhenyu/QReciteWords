package tzy.qrecitewords.dataUtils.serivce;

import android.database.Cursor;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.sql.Date;

import tzy.qrecitewords.dataUtils.dbutils.DataBaseConfigure;
import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.javabean.Library_Table;
import tzy.qrecitewords.javabean.MissionOfDay;
import tzy.qrecitewords.javabean.MissionOfDay_Adapter;
import tzy.qrecitewords.javabean.MissionOfDay_Table;

/**
 * Created by tzy on 2016/5/9.
 */
public class MissionService {
    /**查询学习今日任务，如果没有则生成一个**/
    public static MissionOfDay queryTodayMission(Date date){

        MissionOfDay missionOfDay = new Select().from(MissionOfDay.class)
                .where(MissionOfDay_Table.date.eq(date.toString()))
                .querySingle();

        if(missionOfDay != null){
            return missionOfDay;
        }else{
            missionOfDay =new MissionOfDay();
            missionOfDay.setCountOfLearned(0);
            missionOfDay.setDate(date.toString());
            missionOfDay.setTodayWords(60);
            missionOfDay.insert();
            return missionOfDay;
        }
    }
}
