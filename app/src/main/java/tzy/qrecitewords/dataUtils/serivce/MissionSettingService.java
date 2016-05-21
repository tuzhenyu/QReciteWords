package tzy.qrecitewords.dataUtils.serivce;

import com.raizlabs.android.dbflow.sql.language.Select;

import tzy.qrecitewords.javabean.MissionSetting;

/**
 * Created by tzy on 2016/5/17.
 */
public class MissionSettingService {

    public static MissionSetting setMissionDayCount(int count){

        if(count <= 0){
            throw new IllegalArgumentException("每日学习量不能小于等于0");
        }

        MissionSetting setting = new Select().from(MissionSetting.class)
                                 .querySingle();
        if(setting == null){
            setting = new MissionSetting();
            setting.setDayCount(count);
            setting.insert();
            return setting;
        }

        setting.setDayCount(count);
        setting.update();
        return setting;
    }

    public static MissionSetting getMissionSetting(){
        MissionSetting setting = new Select().from(MissionSetting.class)
                .querySingle();
        if(setting == null){
            setting = new MissionSetting();
            setting.setDayCount(60);
            setting.insert();
        }
        return setting;
    }


}
