package tzy.qrecitewords.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 封装了处理Gson的Volley请求类，实现了添加参数的功能
 * Created by tzy on 2015/8/12.
 */
public class GsonRequest<T> extends Request<T> {

    private  Response.Listener<T> mListener;

    private Gson mGson;

    private Class<T> mClass;

    Map<String,String> map;
    public GsonRequest(int method, String url, Class<T> clazz,  Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        mClass = clazz;
        mListener = listener;
    }

    /**
     * 带请求参数的构造函数
     * @param method
     * @param url
     * @param clazz
     * @param map
     * @param listener
     * @param errorListener
     */
    public GsonRequest(int method, String url, Class<T> clazz, Map<String,String> map,Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this( method, url, clazz,  listener,errorListener);
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        mClass = clazz;
        mListener = listener;
       this.map = map;
    }

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.POST, url, clazz, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return  map;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(jsonString, mClass),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }catch(IllegalStateException e)//解析异常
        {
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
