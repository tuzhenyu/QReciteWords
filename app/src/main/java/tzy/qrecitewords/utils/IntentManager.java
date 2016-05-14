package tzy.qrecitewords.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import tzy.qrecitewords.LibraryWordsActivity;
import tzy.qrecitewords.javabean.Library;
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

    public static void intentToLibraryDetails(Context context){
        Intent intent = new Intent(context, LibraryWordsActivity.class);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
