package tzy.qrecitewords.javabean;

import android.support.annotation.BoolRes;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

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
    @Unique
    public String libraryName;

    @Column
    public String introdu;//介绍信息

    @Column
    //**0代表不存在，1代表存在*/
    public int isExist;

    @Column
    public Date createdTime;

    @Column(defaultValue = "false")
    @NotNull
    public boolean isSelected;

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {

        if(o == null)return false;
        if(this == o)return true;

        if(o instanceof Library){
            if(this.getLibraryName().equals(((Library) o).getLibraryName())){
                return true;
            }
        }
        return false;
    }
}
