package tzy.qrecitewords.dataUtils.serivce;

import android.database.Cursor;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.sql.Date;
import java.util.List;

import tzy.qrecitewords.dataUtils.dbutils.DataBaseConfigure;
import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.javabean.Library_Table;
import tzy.qrecitewords.javabean.MissionOfDay;
import tzy.qrecitewords.javabean.MissionOfDay_Adapter;
import tzy.qrecitewords.javabean.MissionOfDay_Table;
import tzy.qrecitewords.javabean.MissionSetting;
import tzy.qrecitewords.javabean.Sentence;

/**
 * Created by tzy on 2016/5/9.
 */
public class MissionService {
    /**查询学习今日任务，如果没有则生成一个**/
    public static MissionOfDay queryTodayMission(Date date){
       if(date == null){
           date = new Date(System.currentTimeMillis());
       }

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
            return new Select().from(MissionOfDay.class)
                    .where(MissionOfDay_Table.date.eq(date.toString()))
                    .querySingle();
        }
    }

    public static Cursor queryTodayMissionC(Date date){
        Cursor cursor = new Select().from(MissionOfDay.class)
                .where(MissionOfDay_Table.date.eq(date.toString())).query();
        if(cursor.getCount() <= 0){
            cursor.close();
            MissionSetting setting = MissionSettingService.getMissionSetting();
            MissionOfDay missionOfDay =new MissionOfDay();
            missionOfDay.setCountOfLearned(0);
            missionOfDay.setDate(date.toString());
            missionOfDay.setTodayWords(setting.getDayCount());
            missionOfDay.insert();
            cursor = new Select().from(MissionOfDay.class)
                    .where(MissionOfDay_Table.date.eq(date.toString())).query();
        }
            return cursor;
    }

    public static void increProMissionOfDay(int diff,Date date){
        if(date == null){date = new Date(System.currentTimeMillis());}
        MissionOfDay missionOfDay = queryTodayMission(date);
        missionOfDay.setCountOfLearned(missionOfDay.getCountOfLearned() + diff);

        if(missionOfDay.getCountOfLearned() > missionOfDay.getTodayWords()){
            throw new IllegalStateException("已学习的单词数不能大于今日任务数");
        }

        missionOfDay.update();
    }

    public static void addToDayMission(){
        MissionOfDay missionOfDay = MissionService.queryTodayMission(null);
        missionOfDay.setTodayWords(missionOfDay.getTodayWords() + 60);
        missionOfDay.update();
    }

    public static List<MissionOfDay> getMissionPfRange(Date fdate,Date ldate){
        List<MissionOfDay> missionOfDayList = new Select().from(MissionOfDay.class)
                .where(MissionOfDay_Table.date.lessThanOrEq(ldate.toString()))
                .and(MissionOfDay_Table.date.greaterThanOrEq(fdate.toString()))
                .queryList();
        return missionOfDayList;
    }

    public static void getMissionPfRangeAsync(Date fdate, Date ldate, QueryTransaction.QueryResultListCallback<MissionOfDay> callbach){

        new Select().from(MissionOfDay.class)
                .where(MissionOfDay_Table.date.lessThanOrEq(ldate.toString()))
                .and(MissionOfDay_Table.date.greaterThanOrEq(fdate.toString()))
                .orderBy(MissionOfDay_Table.date,true)
                .async()
                .queryListResultCallback(callbach)
                .execute();

    }
}
