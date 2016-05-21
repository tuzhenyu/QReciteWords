package tzy.qrecitewords.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tzy on 2016/5/18.
 */
public class TextChecker {
    /**判断是否包含中文*/
    public static boolean isChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(str);
        if(m.find()){
            temp =  true;
        }
        return temp;
    }
}
