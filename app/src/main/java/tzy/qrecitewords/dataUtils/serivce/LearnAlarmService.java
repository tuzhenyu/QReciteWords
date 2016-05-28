package tzy.qrecitewords.dataUtils.serivce;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.sql.Date;
import java.util.Calendar;

import tzy.qrecitewords.app.WordsApp;
import tzy.qrecitewords.javabean.LearnAlarmSetting;
import tzy.qrecitewords.serivce.AlarmService;

/**
 * Created by tzy on 2016/5/17.
 */
public class LearnAlarmService  {

    public static LearnAlarmSetting setLearnAlarmSwitch(Context context,boolean isopen)
    {
        LearnAlarmSetting setting = new Select()
                .from(LearnAlarmSetting.class)
                .querySingle();
        if(setting == null){
            setting =new LearnAlarmSetting();
            setting.setHour(20);//默认时间
            setting.setMinute(30);
            setting.setOpen(isopen);
            setting.insert();
            setLearnAlarm(context,setting);
            return setting;
        }

        setting.setOpen(isopen);
        setting.update();
        setLearnAlarm(context,setting);
        return setting;
    }

    public static LearnAlarmSetting setLearnAlarm(Context context,int hour,int minute)
    {
        LearnAlarmSetting setting = new Select()
                                   .from(LearnAlarmSetting.class)
                                   .querySingle();
        if(setting == null){
            setting =new LearnAlarmSetting();
            setting.setHour(hour);
            setting.setMinute(minute);
            setting.insert();
            setLearnAlarm(context,setting);
            return setting;
        }

        setting.setHour(hour);
        setting.setMinute(minute);
        setting.update();
        setLearnAlarm(context,setting);
        return setting;
    }

    public static LearnAlarmSetting getLearnAlarmSetting(Context context){

        LearnAlarmSetting setting = new Select()
                .from(LearnAlarmSetting.class)
                .querySingle();
        if(setting == null){
            setting =new LearnAlarmSetting();
            setting.setHour(20);//默认值
            setting.setMinute(30);
            setting.setOpen(true);
            setting.insert();
            setLearnAlarm(context,setting);
        }
        return setting;
    }

    public static String action_learn_alarm = "action_learn_alarm";
    public static String para_alarm_mag = "para_alarm_mag";
    public static void setLearnAlarm(Context context,LearnAlarmSetting setting){
        Intent intent = new Intent(context.getApplicationContext(), AlarmService.class);
        intent.setAction(para_alarm_mag);
        intent.putExtra(para_alarm_mag," 快背单词 提醒您：快来背单词咯！");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getService(
                context.getApplicationContext(), 0, intent, 0);
        if(setting.isOpen()){

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,setting.getHour());
            calendar.set(Calendar.MINUTE,setting.getMinute());

            am.setRepeating(AlarmManager.RTC,
                    calendar.getTimeInMillis(), 1000*3600*24l, sender)
            cancleAlarmBefore(context);
        }else{
            am.cancel(sender);
        }
    }

    public static void cancleAlarmBefore(Context context){
        LearnAlarmSetting setting = new Select()
                .from(LearnAlarmSetting.class)
                .querySingle();
        if(setting != null){
            Intent intent = new Intent(context.getApplicationContext(), AlarmService.class);
            intent.setAction(para_alarm_mag);
            intent.putExtra(para_alarm_mag," 快背单词 提醒您：快来背单词咯！");
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent sender = PendingIntent.getService(
                    context.getApplicationContext(), 0, intent, 0);
            am.cancel(sender);
        }
    }
}
