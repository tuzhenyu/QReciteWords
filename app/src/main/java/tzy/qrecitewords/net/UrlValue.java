package tzy.qrecitewords.net;

/**
 * Created by tzy on 2016/5/7.
 */
public class UrlValue {

    public static final String HOST = "";

    public static final String HTTP_PRO = "http://";

    public static final String serviceName = "QRWord";

    public static final String getLibraries = "getLibraries";

    public static String getLibrariesUrl(){
        StringBuilder url = new StringBuilder(HTTP_PRO);

        url.append(HOST);
        url.append("/");

        url.append(serviceName);
        url.append("/");

        url.append(getLibraries);

        return url.toString();
    }
}
