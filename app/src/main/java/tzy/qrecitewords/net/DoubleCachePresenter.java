package tzy.qrecitewords.net;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * 处理有双缓存逻辑的Presenter
 *
 * 主要逻辑：
 * 1.先加载本地缓存，如果本地成功加载出来，则显示出来
 * 2.等本地缓存加载完成后，加载网络数据
 * 3.网络数据加载成功后，则显示网络数据和本地缓存的同步数据
 * 4.网络数据加载失败，要采取对用措施
 *
 * Created by tzy on 2016/5/7.
 */
public abstract class DoubleCachePresenter<D> {

    public enum LoadType{CACHE,NO_CACHE;}

    public LoadType mLoadType = LoadType.NO_CACHE;

    /**
     * Activity或者Fragment的软引用
     */
    SoftReference<DoubleCacheIView<D>> iViewp;

    /**
     * 请求队列
     */
    public RequestQueue queue;

    D data;

    public DoubleCachePresenter(DoubleCacheIView<D> iView, RequestQueue queue,LoadType laodType)
    {
        mLoadType = laodType;
        if(iView!=null) iViewp = new  SoftReference<DoubleCacheIView<D>>(iView);
        this.queue = queue;

    }

    /**直接从网络获取数据*/
    public void loadDataNoCache(){
        preRequestDataFromNet();
        requestDataFromNet();
    }

    /**以双缓存的模式去加载数据*/
    public void loadDataWithCache(){
        mLoadType = LoadType.CACHE;
        preLoadLocalData();
        loadLocalData();
    }

    public abstract void preLoadLocalData();

    public abstract void loadLocalData();

    public  void postLocalData(D data){

        DoubleCacheIView view = null;
        if(iViewp == null || (view = iViewp.get()) == null){return;}
        view.showLocalData(data);

        if(mLoadType == LoadType.CACHE){
           loadDataNoCache();
       }
    }

    public abstract void preRequestDataFromNet();

    public abstract void requestDataFromNet();

    public  void postRequestDataFromNet(D data,VolleyError error){
        DoubleCacheIView view = null;
        if(iViewp == null || (view = iViewp.get()) == null){return;}

        if(error != null){
            view.showErrorMsg(error.getMessage());
            return;
        }

        view.showNetData(data);
    }

    /**数据处理*/

    public abstract void dataDealFromNet(D data);

    /**net*/
    public  void preRequestMore(){}
    /**net*/
    public void  requestMore(){}
    /**net*/
    public void  postRequestMore(D data,VolleyError error){}

    /**
     * 请求数据
     * @param req url请求
     * @param dClass 需要将json数据封装成的对象的类信息
     */
    public void  requestData(String req,Class<D> dClass)
    {
        GsonRequest<D> request = new GsonRequest<D>(Request.Method.GET, req, dClass, new Response.Listener<D>() {
            @Override
            public void onResponse(final D d) {
                 dataDealFromNet(d);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                postRequestDataFromNet(null,volleyError);
            }
        });

        queue.add(request);
    }

    /**
     *
     * @param req
     * @param para 请求参数
     * @param dClass
     */
    public void  requestData(String req, final Map para, Class<D> dClass)
    {
        GsonRequest<D> request = new GsonRequest<D>(Request.Method.POST, req, dClass,para, new Response.Listener<D>() {
            @Override
            public void onResponse(final D d) {
                dataDealFromNet(d);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                postRequestDataFromNet(null,volleyError);
            }
        });

        queue.add(request);
    }

    /**
     * 请求更多数据
     * @param req
     * @param para
     * @param dClass
     */
    public void requesMoreData(String req, final Map para,Class<D> dClass)
    {
        GsonRequest<D> request = new GsonRequest<D>(Request.Method.POST, req, dClass,para, new Response.Listener<D>() {
            @Override
            public void onResponse(D d) {
                postRequestMore(d,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                postRequestMore(null,volleyError);
            }
        });

        queue.add(request);
    }

    public  DoubleCacheIView getIView(){
        return iViewp.get();
    }

    public Context getContext()
    {
        if(iViewp!=null && iViewp.get()!=null)
        {
            if(Fragment.class.isAssignableFrom(iViewp.get().getClass()))
                return  ((Fragment) iViewp.get()).getActivity();
            else
                return ((Activity) iViewp.get());
        }
        return null;
    }

    public LoadType getmLoadType() {
        return mLoadType;
    }

    public void setmLoadType(LoadType mLoadType) {
        this.mLoadType = mLoadType;
    }

    public void destory(){
        if(queue != null){
            queue.stop();
            queue = null;
        }

        iViewp.clear();
        data = null;
    }


}
