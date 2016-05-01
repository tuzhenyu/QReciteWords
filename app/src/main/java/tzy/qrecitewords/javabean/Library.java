package tzy.qrecitewords.javabean;

import java.sql.Date;

/**
 * 词库
 * Created by tzy on 2016/2/24.
 */
public class Library {

    private String libraryName;
    /**0代表不存在，1代表存在*/
    private int isExist;

    private Date createdTime;

    public Library(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getIsExist() {
        return isExist;
    }

    public void setIsExist(int isExist) {
        this.isExist = isExist;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
