package tzy.qrecitewords.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import tzy.qrecitewords.widget.ReciteWordsActivity;

/**
 * Created by tzy on 2016/4/11.
 */
public class IntentManager {

    /**跳转到背单词的界面*/
    public static void intentToReciteWrdsActivity(Context context,Bundle bundle){
     Intent intent = new Intent(context, ReciteWordsActivity.class);
     if(null != bundle){
         intent.putExtras(bundle);
     }
     context.startActivity(intent);

    }
}
