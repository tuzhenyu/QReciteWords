package tzy.qrecitewords.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import tzy.qrecitewords.AnalyzeActivity;
import tzy.qrecitewords.LibraryWordsActivity;
import tzy.qrecitewords.MainActivity;
import tzy.qrecitewords.MissionSettingActivity;
import tzy.qrecitewords.ReciteWordsActivity;
import tzy.qrecitewords.javabean.Library;

/**
 * Created by tzy on 2016/4/11.
 */
public class IntentManager {

    /**跳转到背单词的界面
     * @param library 相应词库
     * @param famility 对应词组
     * */
    public static void intentToReciteWrdsActivity(Context context,Library library,int famility){
        Intent intent = new Intent(context, ReciteWordsActivity.class);
        intent.putExtra(ReciteWordsActivity.PARA_LIBRARY,library);
        intent.putExtra(ReciteWordsActivity.PARA_FAMLIITY,famility);

        context.startActivity(intent);

    }

    public static final String PARA_LIBRARY = "PARA_LIBRARY";
    public static void intentToLibraryDetails(Context context,Library library){
        Intent intent = new Intent(context, LibraryWordsActivity.class);
        intent.putExtra(PARA_LIBRARY,library);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }



    public static void intentMIssionSettingActivity(Context context){
        Intent intent = new Intent(context, MissionSettingActivity.class);
        context.startActivity(intent);
    }

    public static void intentAnaylyzeActivity(Context context){
            Intent intent = new Intent(context, AnalyzeActivity.class);
        context.startActivity(intent);
    }
}
