package tzy.qrecitewords.dataUtils.serivce;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.sql.Date;

import tzy.qrecitewords.javabean.MissionOfDay;
import tzy.qrecitewords.javabean.MissionOfDay_Table;

/**
 * Created by tzy on 2016/5/9.
 */
public class MissionService {
    /**查询学习今日任务，如果没有则生成一个**/
    public static MissionOfDay queryTodayMission(Date date){
       MissionOfDay missionOfDay = new Select().from(MissionOfDay.class)
                    .where(MissionOfDay_Table.date.eq(date))
                    .querySingle();
        if(missionOfDay == null){//今日任务还没有生成，则生成一个
            missionOfDay =new MissionOfDay();
            missionOfDay.setCountOfLearned(0);
            missionOfDay.setDate(date);
            missionOfDay.setTodayWords(60);
            missionOfDay.insert();
            return missionOfDay;
        }
        return missionOfDay;
    }
}
