package tzy.qrecitewords.javabean;

import android.os.Parcel;
import android.os.Parcelable;
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
public class Library extends BaseModel implements Parcelable {

    public interface IsExist{
        int exist = 1;
        int noExist = 0;
    }

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
    @NotNull
    public String introdu;//介绍信息

    @Column(defaultValue = "0")
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

    int countOfTotal = 0;//所有单词数

    protected Library(Parcel in) {
        libraryName = in.readString();
        introdu = in.readString();
        isExist = in.readInt();
        createdTime = in.readLong();
        isSelected = in.readByte() != 0;
        countNoRead = in.readInt();
        countNoKnown = in.readInt();
        countFam = in.readInt();
        countNoFam = in.readInt();
        countOfTotal = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(libraryName);
        dest.writeString(introdu);
        dest.writeInt(isExist);
        dest.writeLong(createdTime);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(countNoRead);
        dest.writeInt(countNoKnown);
        dest.writeInt(countFam);
        dest.writeInt(countNoFam);
        dest.writeInt(countOfTotal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Library> CREATOR = new Creator<Library>() {
        @Override
        public Library createFromParcel(Parcel in) {
            return new Library(in);
        }

        @Override
        public Library[] newArray(int size) {
            return new Library[size];
        }
    };

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

    public void changeFamility(int oldFam,int newFam){
        switch(oldFam){
            case Library.Familiarity.familary:
                --countFam;
                break;
            case Library.Familiarity.nofamilary:
                --countNoFam;
                break;
            case Library.Familiarity.noknown:
                --countNoKnown;
                break;
            case Library.Familiarity.noRead:
                --countNoRead;
                break;
        }

        switch(newFam){
            case Library.Familiarity.familary:
                ++countFam;
                break;
            case Library.Familiarity.nofamilary:
                ++countNoFam;
                break;
            case Library.Familiarity.noknown:
                ++countNoKnown;
                break;
            case Library.Familiarity.noRead:
                ++countNoRead;
                break;
        }
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
