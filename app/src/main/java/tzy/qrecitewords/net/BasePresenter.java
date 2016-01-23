package tzy.qrecitewords.net;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.lang.ref.SoftReference;
import java.util.Map;
/**
 * 基本的控制器，负责控制UI与业务层的交互
 */

/**
 * Created by tzy on 2015/8/15.
 */
public class BasePresenter<D> {

    /**
     * Activity或者Fragment的软引用
     */
    SoftReference<IView<String, D>> iViewp;

    /**
     * 请求队列
     */
    public RequestQueue queue;

    public BasePresenter(IView<String, D> iView, RequestQueue queue)
    {
        if(iView!=null) iViewp = new  SoftReference<IView<String, D>>(iView);
        this.queue = queue;

    }

    /**
     * 请求数据
     * @param req url请求
     * @param dClass 需要将json数据封装成的对象的类信息
     */
    public void  requestData(String req,Class<D> dClass)
    {
        GsonRequest<D> request = new GsonRequest<D>(Request.Method.POST, req, dClass, new Response.Listener<D>() {
            @Override
            public void onResponse(D d) {
                loadData(d);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loadData(null);
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
    public void  requestData(String req, final Map para,Class<D> dClass)
    {
        GsonRequest<D> request = new GsonRequest<D>(Request.Method.POST, req, dClass,para, new Response.Listener<D>() {
            @Override
            public void onResponse(D d) {
                loadData(d);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loadData(null);
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
                loadMoreData(d);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loadMoreData(null);
            }
        });

        queue.add(request);
    }

    /**
     * 调用UI加载数据
     * @param d
     */
    void loadData(D d)
    {
        if(iViewp ==null||iViewp.get()==null)return;

        IView mIvew = iViewp.get();

        mIvew.loadData(d);
    }

    /**
     * 调用UI加载更多书数据
     * @param d
     */
    void loadMoreData(D d)
    {
        if(iViewp ==null||iViewp.get()==null)return;

        IView mIvew = iViewp.get();

        mIvew.loadMoreData(d);
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
}
