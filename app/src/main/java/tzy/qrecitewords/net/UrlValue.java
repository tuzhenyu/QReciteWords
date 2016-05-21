package tzy.qrecitewords.net;

/**
 * Created by tzy on 2016/5/7.
 */
public class UrlValue {

    public static final String HOST = "10.10.114.22";

    public static final String port = ":8080";

    public static final String HTTP_PRO = "http://";

    public static final String serviceName = "QwordServer";

    public static final String getLibraries = "getLibraries";

    public static final String downloadUrl = "download/library/";

    public static final String fileSuffix = ".txt";

    public static String getLibrariesUrl(){
        StringBuilder url = new StringBuilder(HTTP_PRO);

        url.append(HOST);

        url.append(port);

        url.append("/");

        url.append(serviceName);
        url.append("/");

        url.append(getLibraries);

        return url.toString();
    }

    public static String getDownLoadUrl(String fileName){
        StringBuilder url = new StringBuilder(HTTP_PRO);

        url.append(HOST);

        url.append(port);

        url.append("/");

        url.append(serviceName);
        url.append("/");

        url.append(downloadUrl);

        url.append(fileName);

        url.append(fileSuffix);

        return url.toString();
    }

}
