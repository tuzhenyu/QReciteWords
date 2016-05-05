package tzy.qrecitewords.javabean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;


/**
 * 词库
 * Created by tzy on 2016/2/24.
 */
@ModelContainer
@Table(database = WordDataBase.class)
public class Library extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public Long id;

    @Column
    @NotNull
    public String libraryName;

    @Column
    public String introdu;//介绍信息

    @Column
    //**0代表不存在，1代表存在*/
    public int isExist;

    @Column
    public Long createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntrodu() {
        return introdu;
    }

    public void setIntrodu(String introdu) {
        this.introdu = introdu;
    }

    public Library(String libraryName) {
        this.libraryName = libraryName;
    }

    public Library(){}
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

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
}
