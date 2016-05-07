package tzy.qrecitewords.net;

/**
 * Created by tzy on 2016/5/7.
 */
public interface DoubleCacheIView<Result> {

    public static  int GETMethod = 0;

    public static  int POSTMethod = 1;

    /**
     * 请求数据必须实现的方法
     */
    void rquestData();

    /**
     * 请求更多数据
     * @param page 页码
     */
    void requestMoreData( int page);
    /**
     * 加载数据必须实现的方法
     */
    void showLocalData(Result result);

    void showNetData(Result result);

    /**
     * 加载更多数据
     * @param data
     */
    void showMoreData(Result data);

    void showErrorMsg(String error);
}
