package tzy.qrecitewords.net;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.android.volley.RequestQueue;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by tzy on 2016/5/14.
 */
public abstract class CustomerPresenter<D> {
    /**
     * Activity或者Fragment的软引用
     */

    public static class Error{
        int errorCode;

        String errorMsg;

        public Error(int errorCode, String errorMsg) {
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

    public interface ResultListener<D>{
        void onSuccess(D result);

        void onFail(Error error);
    }

    public interface ResultsListener<D>{
        void onSuccess(List<D> result);

        void onFail(Error error);
    }

    public CustomerPresenter() {}

    public  void requestData(ResultListener resultListener){

    }

    public  void showData(D data){

    }

    public  void requestDataList(ResultsListener resultListener){

    }

    public  void showDatas(List<D> datas){

    }

}
