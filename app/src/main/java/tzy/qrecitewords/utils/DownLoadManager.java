package tzy.qrecitewords.utils;

import android.content.Context;
import android.content.Intent;

import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.serivce.LibraryDownLoadService;

/**
 * Created by tzy on 2016/5/11.
 */
public class DownLoadManager {
    Context mContext;

    LibraryDownLoadService downLoader;

    public DownLoadManager(Context context){
        mContext = context;
    }

    public void startDownLoad(Library library){
        Intent intent = new Intent(mContext,LibraryDownLoadService.class);
        intent.setAction("com.tzy.downloadLibrary");
        intent.putExtra(LibraryDownLoadService.PAR_LIBRARY,library);
        mContext.startService(intent);
    }

    public void cancleDownLoad(){
        Intent intent = new Intent(mContext,LibraryDownLoadService.class);
        intent.setAction("com.tzy.downloadLibrary");
        mContext.stopService(intent);
    }
}
