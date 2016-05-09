package tzy.qrecitewords.javabean;

import android.database.Cursor;
import android.text.TextUtils;

/**
 * Created by tzy on 2016/5/1.
 */
public class LibraryInfo {
   /* *//**
     * 熟悉程度
     * 1代表熟悉；
     * 2代表不熟悉；
     * 3代表很不熟悉，
     * 0代表没读过
     * *//*
    public interface Familiarity{
        int noRead = 0;
        int familary = 1;
        int nofamilary = 2;
        int noknown = 3;
    }

    String LibraryIntrodu;//词库名称

    int countNoRead ;//未读单词的数量

    int countNoKnown;//陌生单词的数量

    int countFam;//熟悉单词的数量

    int countNoFam;//有点陌生的单词的数量

    int countOfTotal;//所有单词数

    public static LibraryInfo CursorToLibraryInfo(String LibraryIntrodu,Cursor cursor){
        if(TextUtils.isEmpty(LibraryIntrodu) || cursor == null ){return null;}
        LibraryInfo info = new LibraryInfo();
        info.setLibraryIntrodu(LibraryIntrodu);
        while(cursor.moveToNext()){
            int familiarity = cursor.getInt(0);
            int count = cursor.getInt(1);
            switch(familiarity){
                case Familiarity.noRead:
                    info.countNoRead = count;
                    break;
                case Familiarity.familary:
                    info.countFam = count;
                    break;
                case Familiarity.nofamilary:
                    info.countNoFam = count;
                    break;
                case Familiarity.noknown:
                    info.countNoKnown = count;
                    break;
            }
        }
        int total = info.getCountFam() + info.getCountNoFam() +
                    info.getCountNoKnown() + info.getCountNoRead();
        info.setCountOfTotal(total);
        return info;
    }

    public int getCountOfTotal() {
        return countOfTotal;
    }

    public void setCountOfTotal(int countOfTotal) {
        this.countOfTotal = countOfTotal;
    }

    public String getLibraryIntrodu() {
        return LibraryIntrodu;
    }

    public void setLibraryIntrodu(String libraryIntrodu) {
        LibraryIntrodu = libraryIntrodu;
    }

    public int getCountNoRead() {
        return countNoRead;
    }

    public void setCountNoRead(int countNoRead) {
        this.countNoRead = countNoRead;
    }

    public int getCountNoKnown() {
        return countNoKnown;
    }

    public void setCountNoKnown(int countNoKnown) {
        this.countNoKnown = countNoKnown;
    }

    public int getCountFam() {
        return countFam;
    }

    public void setCountFam(int countFam) {
        this.countFam = countFam;
    }

    public int getCountNoFam() {
        return countNoFam;
    }

    public void setCountNoFam(int countNoFam) {
        this.countNoFam = countNoFam;
    }*/
}
