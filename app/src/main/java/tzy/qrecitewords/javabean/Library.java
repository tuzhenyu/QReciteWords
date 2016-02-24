package tzy.qrecitewords.javabean;

/**
 * 词库
 * Created by tzy on 2016/2/24.
 */
public class Library {

  private String libraryName;

    public Library(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
}
