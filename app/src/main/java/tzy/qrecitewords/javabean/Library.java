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

    /**
     * 熟悉程度
     * 1代表熟悉；
     * 2代表不熟悉；
     * 3代表很不熟悉，
     * 0代表没读过
     * */
    public interface Familiarity{
        int noRead = 0;
        int familary = 1;
        int nofamilary = 2;
        int noknown = 3;
    }


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
    public long createdTime;

    @Column(defaultValue = "false")
    @NotNull
    public boolean isSelected;

    @Column(defaultValue = "0")
    int countNoRead ;//未读单词的数量
    @Column(defaultValue = "0")
    int countNoKnown;//陌生单词的数量
    @Column(defaultValue = "0")
    int countFam;//熟悉单词的数量
    @Column(defaultValue = "0")
    int countNoFam;//有点陌生的单词的数量

    int countOfTotal;//所有单词数

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

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCountNoRead() {
        return countNoRead;
    }

    public void setCountNoRead(int countNoRead) {
        this.countNoRead = countNoRead;
    }

    public int getCountOfTotal() {
        return countOfTotal;
    }

    public void setCountOfTotal(int countOfTotal) {
        this.countOfTotal = countOfTotal;
    }

    public int getCountNoFam() {
        return countNoFam;
    }

    public void setCountNoFam(int countNoFam) {
        this.countNoFam = countNoFam;
    }

    public int getCountFam() {
        return countFam;
    }

    public void setCountFam(int countFam) {
        this.countFam = countFam;
    }

    public int getCountNoKnown() {
        return countNoKnown;
    }

    public void setCountNoKnown(int countNoKnown) {
        this.countNoKnown = countNoKnown;
    }

    @Override
    public boolean equals(Object o) {

        if(o == null)return false;
        if(this == o)return true;

        if(o instanceof Library){
            Library rValue = (Library) o;
            if(this.id == rValue.id){return true;}
            if(this.getLibraryName().equals(((Library) o).getLibraryName())){
                return true;
            }
        }
        return false;
    }
}
