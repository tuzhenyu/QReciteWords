package tzy.qrecitewords.javabean;

import android.database.Cursor;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by tzy on 2016/5/1.
 */
public class LibraryInfo {
    String libraryName;
    int version;
    List<Word> words;
    public String getLibraryName() {
        return libraryName;
    }
    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public List<Word> getWords() {
        return words;
    }
    public void setWords(List<Word> words) {
        this.words = words;
    }
}
