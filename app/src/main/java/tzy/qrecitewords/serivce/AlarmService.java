package tzy.qrecitewords.serivce;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import tzy.qrecitewords.R;
import tzy.qrecitewords.dataUtils.serivce.LearnAlarmService;

/**
 * Created by tzy on 2016/5/17.
 */
public class AlarmService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            //消息通知栏
            //定义NotificationManager
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //定义通知栏展现的内容信息
            CharSequence tickerText = "快背单词";
            long when = System.currentTimeMillis();
            Notification notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.book_icon)
                    .setContentText(tickerText)
                    .setContentText(intent.getStringExtra(LearnAlarmService.para_alarm_mag))
                    .setTicker("背单词啦")
                    .build();

            //定义下拉通知栏时要展现的内容信息
            //用mNotificationManager的notify方法通知用户生成标题栏消息通知
            mNotificationManager.notify(1, notification);

        return super.onStartCommand(intent, flags, startId);
    }


}
