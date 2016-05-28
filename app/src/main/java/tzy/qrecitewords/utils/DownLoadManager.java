package tzy.qrecitewords.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Handler;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.dataUtils.serivce.WordSerivce;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.LibraryInfo;
import tzy.qrecitewords.net.UrlValue;
import tzy.qrecitewords.serivce.LibraryDownLoadService;

/**
 * Created by tzy on 2016/5/11.
 */
public class DownLoadManager {
    Context mContext;

    LibraryDownLoadService downLoader;

    DatabaseWrapper wrapper = null;

    HttpURLConnection conn = null;

    DownLoadTask downLoadTask;

    public DownLoadManager(Context context) {
        mContext = context;
        executor = Executors.newSingleThreadExecutor();
    }

    ExecutorService executor;

    public void startDownLoad(Library library, android.os.Handler handler) {
        // Intent intent = new Intent(mContext,LibraryDownLoadService.class);
        // intent.setAction("com.tzy.downloadLibrary");
        // intent.putExtra(LibraryDownLoadService.PAR_LIBRARY,library);
        //mContext.startService(intent);
        ExecutorService executorService = getExecutorSerivce();
        downLoadTask = new DownLoadTask(mContext,library,handler);
        executorService.submit(downLoadTask);
    }

    public ExecutorService getExecutorSerivce() {
        if (executor == null || executor.isShutdown() || executor.isTerminated()) {
            executor = Executors.newSingleThreadExecutor();
        }
        return executor;
    }

    public void cancleDownLoad() {
        // Intent intent = new Intent(mContext,LibraryDownLoadService.class);
        // intent.setAction("com.tzy.downloadLibrary");
        // mContext.stopService(intent);
        if (executor != null) {
            executor.shutdownNow();
            downLoadTask.stopService();
        }
    }

    public static class DownLoadTask implements Runnable {

        WeakReference<Context> contextWf;

        public static final String TAG = "DownLoadTask";
        public static final String ACTION_LIBRARY_DOWNLOAD_REQUEST = "ACTION_LIBRARY_DOWNLOAD_REQUEST";
        public static final String PAR_LIBRARY_NAME = "PAR_LIBRARY_NAME";
        public static final String PAR_PROGRESS = "PAR_PROGRESS";

        public static final String DOWNLOAD_SUCCESS = "DownLOAD_SUCCESS";

        public static final String SAVE_lIBRARY_SUCCESS = "SAVE_lIBRARY_SUCCESS";

        public static final String PAR_LIBRARY = "PAR_LIBRARY";

        public static final String action_download_porgress = "action_download_porgress";
        public static final String action_download_complete = "action_download_complete";
        public static final String action_store_complete = "action_store_result";
        DatabaseWrapper wrapper = null;

        HttpURLConnection conn = null;

        Library library;

        android.os.Handler mHandler;

        public DownLoadTask(Context context, Library library,android.os.Handler handler) {
            this.contextWf = new WeakReference<Context>(context);
            this.library = library;
            mHandler = handler;
        }

        @Override
        public void run() {
            //读取服务器的文件流
            //转化为JSon
            //数据库事务存储
            //数据库事务员：1.存入词库信息，2.批量插入单词
            String urlStr = UrlValue.getDownLoadUrl(library.getLibraryName());
            Log.i(TAG, "currentThread:" + Thread.currentThread().getName());
            try {
                /*
                 * 通过URL取得HttpURLConnection
                 * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
                 * <uses-permission android:name="android.permission.INTERNET" />
                 */

                URL url = new URL(urlStr);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(5 * 1000);
                conn.setRequestProperty("keepAlive", "false");
                //取得inputStream，并进行读取
                InputStream input = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));

                float fileLength = conn.getContentLength() / 8;//获得文件大小（以字符为单位）
                float readLength = 0;
                String line = null;
                StringBuffer sb = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    readLength += line.length();
                    int porgress = (int) (readLength / fileLength * 100);

                    if (porgress % 10 == 0) {
                        sendBroadcast(porgress);
                    }
                }
                in.close();

                Log.i(TAG, "词库下载完成");
                sendDownLoadBroadcast(true);
                //存储词库到本地
                 LibraryInfo info = new Gson().fromJson(sb.toString(),LibraryInfo.class);

                ITransaction iTransaction = WordSerivce.insertNewLibraryITransaction(info, library);//保存词库

                wrapper = FlowManager.getDatabase(WordDataBase.class).getWritableDatabase();
                try {
                    wrapper.beginTransaction();
                    iTransaction.execute(wrapper);
                    wrapper.setTransactionSuccessful();
                    sendStoreBroadcast(true,library);
                    Log.i(TAG, "词库处理完成");
                } catch (Exception e) {
                    e.printStackTrace();
                    sendStoreBroadcast(false,null);
                } finally {
                    wrapper.endTransaction();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                sendDownLoadBroadcast(false);
            } catch (IOException e) {
                e.printStackTrace();
                sendDownLoadBroadcast(false);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }

        void sendDownLoadBroadcast(boolean isSuccess) {
            Intent intentD = new Intent(action_download_complete);
            intentD.putExtra(DOWNLOAD_SUCCESS, isSuccess);
            contextWf.get().sendBroadcast(intentD);
        }

        void sendStoreBroadcast(boolean isSuccess, Library library) {
            if(mHandler != null){
                Message msg = Message.obtain(mHandler,2);
                Bundle bundle = new Bundle();
                bundle.putBoolean(SAVE_lIBRARY_SUCCESS,isSuccess);
                if (isSuccess) {
                    bundle.putParcelable(PAR_LIBRARY, library);
                }
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
            /*Intent intentS = new Intent(action_store_complete);
            intentS.putExtra(SAVE_lIBRARY_SUCCESS, isSuccess);
            if (isSuccess) {
                intentS.putExtra(PAR_LIBRARY, library);
            }
            contextWf.get().sendBroadcast(intentS);*/
        }

        Intent mIntent;

        public Intent getIntentForPrgress(int progress) {
            if (mIntent == null) {
                mIntent = new Intent(action_download_porgress);
            }
            mIntent.putExtra(PAR_PROGRESS, progress);

            return mIntent;
        }

        public void sendBroadcast(int progress) {
            getIntentForPrgress(progress);
            contextWf.get().sendBroadcast(mIntent);
        }

        public void stopService() {
            if (conn != null) {
                conn.disconnect();
            }
            if (wrapper != null ) {
                try{
                    wrapper.endTransaction();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
            Log.i(TAG, "停止服务了");
        }
    }
}
