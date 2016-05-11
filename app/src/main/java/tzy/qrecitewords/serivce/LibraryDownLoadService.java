package tzy.qrecitewords.serivce;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

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

    public static final String TAG = "LibraryDownLoadService";
    public static final String ACTION_LIBRARY_DOWNLOAD_REQUEST = "ACTION_LIBRARY_DOWNLOAD_REQUEST";
    public static final String PAR_LIBRARY_NAME = "PAR_LIBRARY_NAME";
    public static final String PAR_PROGRESS = "PAR_PROGRESS";

    public static final String DOWNLOAD_SUCCESS = "DownLOAD_SUCCESS";
    public static final String DOWNLOAD_FAILED = "DownLOAD_FAILED";

    public static final String SAVE_lIBRARY_FALIED = "SAVE_lIBRARY_FALIED";
    public static final String SAVE_lIBRARY_SUCCESS = "SAVE_lIBRARY_SUCCESS";

    public static final String PAR_LIBRARY = "PAR_LIBRARY";

    public static final String action_download_porgress = "action_download_porgress";

    DatabaseWrapper wrapper = null;
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
        HttpURLConnection conn = null;
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
            //取得inputStream，并进行读取
            InputStream input=conn.getInputStream();
            BufferedReader in=new BufferedReader(new InputStreamReader(input));

            float fileLength = conn.getContentLength()/8;//获得文件大小（以字符为单位）
            float readLength = 0;
            int read = 0;
            char[] bbuf = new char[2048];
            String line=null;
            StringBuffer sb=new StringBuffer();
            while((read = in.read(bbuf)) > 0){
                sb.append(bbuf);
                readLength += read;
                int porgress = (int) (readLength/fileLength * 100);
                sendBroadcast(porgress);
            }
            in.close();

            //存储词库到本地
            /*LibraryInfo info = new Gson().fromJson(sb.toString(),LibraryInfo.class);
            ITransaction iTransaction = WordSerivce.insertNewLibraryITransaction(info,library);//保存词库

            wrapper = FlowManager.getDatabase(WordDataBase.class).getWritableDatabase();
            try {
                wrapper.beginTransaction();
                iTransaction.execute(wrapper);
                wrapper.setTransactionSuccessful();
            }finally {
                wrapper.endTransaction();
            }
            */
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
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

    @Override
    public boolean stopService(Intent name) {
        if(wrapper != null){
            wrapper.endTransaction();
        }
        return super.stopService(name);
    }
}
