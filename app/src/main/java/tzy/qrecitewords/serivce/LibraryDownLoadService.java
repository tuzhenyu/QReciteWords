package tzy.qrecitewords.serivce;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.dataUtils.serivce.WordSerivce;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.LibraryInfo;
import tzy.qrecitewords.net.UrlValue;

/**
 * Created by tzy on 2016/5/10.
 */
public class LibraryDownLoadService extends IntentService {

     static final String TAG = "LibraryDownLoadService";
     static final String ACTION_LIBRARY_DOWNLOAD_REQUEST = "ACTION_LIBRARY_DOWNLOAD_REQUEST";
     static final String PAR_LIBRARY_NAME = "PAR_LIBRARY_NAME";
     static final String PAR_PROGRESS = "PAR_PROGRESS";

     static final String DOWNLOAD_SUCCESS = "DownLOAD_SUCCESS";

     static final String SAVE_lIBRARY_SUCCESS = "SAVE_lIBRARY_SUCCESS";

     static final String PAR_LIBRARY = "PAR_LIBRARY";

     static final String action_download_porgress = "action_download_porgress";
     static final String action_download_complete = "action_download_complete";
     static final String action_store_complete = "action_store_complete";
    DatabaseWrapper wrapper = null;

    HttpURLConnection conn = null;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LibraryDownLoadService(String name) {
        super(name);
    }

    public LibraryDownLoadService() {
        super("DownLoadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //读取服务器的文件流
        //转化为JSon
        //数据库事务存储
        //数据库事务员：1.存入词库信息，2.批量插入单词

        Library library = intent.getParcelableExtra(PAR_LIBRARY);
        String urlStr= UrlValue.getDownLoadUrl(library.getLibraryName());
        Log.i(TAG,"currentThread:"+Thread.currentThread().getName());
        try {
                /*
                 * 通过URL取得HttpURLConnection
                 * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
                 * <uses-permission android:name="android.permission.INTERNET" />
                 */

            URL url=new URL(urlStr);
            conn =(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            conn.setRequestProperty("keepAlive","false");
            //取得inputStream，并进行读取
            InputStream input=conn.getInputStream();
            BufferedReader in=new BufferedReader(new InputStreamReader(input));

            float fileLength = conn.getContentLength()/8;//获得文件大小（以字符为单位）
            float readLength = 0;
            String line=null;
            StringBuffer sb=new StringBuffer();
            while((line = in.readLine()) != null){
                sb.append(line);
                readLength += line.length();
                int porgress = (int) (readLength/fileLength * 100);
                sendBroadcast(porgress);
            }
            in.close();

            Log.i(TAG,"词库下载完成");
            sendDownLoadBroadcast(true);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //存储词库到本地
            LibraryInfo info = new Gson().fromJson(sb.toString(),LibraryInfo.class);

           /* ITransaction iTransaction = WordSerivce.insertNewLibraryITransaction(info,library);//保存词库

            wrapper = FlowManager.getDatabase(WordDataBase.class).getWritableDatabase();
            try {
                wrapper.beginTransaction();
                iTransaction.execute(wrapper);
                wrapper.setTransactionSuccessful();
                sendStoreBroadcast(true);
            }catch (Exception e){
                e.printStackTrace();
                sendStoreBroadcast(false);
            }finally {
                wrapper.endTransaction();
            }*/

            Log.i(TAG,"词库处理完成");
            sendStoreBroadcast(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            sendDownLoadBroadcast(false);
        } catch (IOException e) {
            e.printStackTrace();
            sendDownLoadBroadcast(false);
        }finally{
            conn.disconnect();
        }
}

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        if(conn != null ){
            conn.disconnect();
        }
        if(wrapper != null){
            wrapper.endTransaction();
        }
        Log.i(TAG,"onDestroy - 停止服务了");
        super.onDestroy();

    }
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    void sendDownLoadBroadcast(boolean isSuccess){
        Intent intentD = new Intent(action_download_complete);
        intentD.putExtra(DOWNLOAD_SUCCESS,isSuccess);
        sendBroadcast(intentD);
    }

    void sendStoreBroadcast(boolean isSuccess){
        Intent intentS = new Intent(action_download_complete);
        intentS.putExtra(SAVE_lIBRARY_SUCCESS,isSuccess);
        sendBroadcast(intentS);
    }
    public void sendBroadcast(int progress){
        getIntentForPrgress(progress);
        sendBroadcast(mIntent);
    }

    Intent mIntent;
    public Intent getIntentForPrgress(int progress){
        if(mIntent == null){
            mIntent = new Intent(action_download_porgress);
        }
        mIntent.putExtra(PAR_PROGRESS,progress);

        return mIntent;
    }

    int startId = 0;



    @Override
    public boolean stopService(Intent name) {
        if(conn != null ){
            conn.disconnect();
        }
        if(wrapper != null){
            wrapper.endTransaction();
        }
        Log.i(TAG,"停止服务了");
        return super.stopService(name);
    }

}
