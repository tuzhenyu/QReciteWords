package tzy.qrecitewords.net;

import java.net.URI;

/**
 * 该接口用于定义了Frament的数据加载
 * Created by Administrator on 2015/7/30.
 */
public interface IView <Request,Result>{

    public static  int GETMethod = 0;

    public static  int POSTMethod = 1;

    /**
     * 请求数据必须实现的方法
     * @param uri request
     */
    void rquestData(Request request);

    /**
     * 请求更多数据
     * @param request
     * @param page 页码
     */
    void requestMoreData(Request request, int page);
    /**
     * 加载数据必须实现的方法
     * @param gsonObject
     */
    void loadData(Result data);

    /**
     * 加载更多数据
     * @param data
     */
    void loadMoreData(Result data);
}
