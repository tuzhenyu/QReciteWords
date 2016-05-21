package tzy.qrecitewords.app;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import cn.refactor.lib.colordialog.PromptDialog;
import tzy.qrecitewords.R;

/**
 * Created by tzy on 2016/4/11.
 */
public abstract class BaseActivity extends Activity {

    View rootView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = LayoutInflater.from(this).inflate(getLayout(),null);
        setContentView(rootView);
        preView();
        initView();
        postInitView();
    }

    public abstract  void preView();

    public abstract  void initView();

    public abstract void postInitView();

    public void showActingPorgress(boolean isShow,String msg){
        if(isShow){
            if(progressDialog == null){
                progressDialog = new ProgressDialog(this);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(R.style.Widget_AppCompat_ProgressBar);
            }
            progressDialog.setMessage(msg);
            progressDialog.show();
        }else{
            if(progressDialog != null){
                progressDialog .dismiss();
            }
        }
    }

    public void showActingPorgress(boolean isShow,int msgId){
        showActingPorgress(isShow,getString(msgId));
    }

    public void showPromoteDialog(String title,String msg, int type,PromptDialog.OnPositiveListener listener){
        PromptDialog dialog = new PromptDialog(this);
        dialog.setAnimationEnable(true);
        if(TextUtils.isEmpty(title)){
            dialog.setTitleShow(false);
        }else{
            dialog.setTitleShow(true);
            dialog.setTitleText(title);
        }
        dialog.setDialogType(type);
        dialog.setContentText(msg);
        if(listener == null){
            listener = new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();
                }
            };
        }
        dialog.setPositiveListener(R.string.sure,listener);
        dialog.show();

    }

    public void showPromoteDialog(int titleSId,int id, int type,PromptDialog.OnPositiveListener listener){
        showPromoteDialog(getString(titleSId),getString(id),type,listener);
    }

    public abstract int getLayout();
}
