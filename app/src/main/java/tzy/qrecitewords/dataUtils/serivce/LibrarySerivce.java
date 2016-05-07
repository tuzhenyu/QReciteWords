package tzy.qrecitewords.dataUtils.serivce;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import tzy.qrecitewords.javabean.Libraries;
import tzy.qrecitewords.javabean.Library;

/**
 * Created by tzy on 2016/5/7.
 */
public class LibrarySerivce {

    /**获取词库列表*/
    public static Libraries getLibrariesLocal(){
        List<Library> list = new Select().from(Library.class).queryList();
        Libraries libraries = new Libraries();
        libraries.setLibraries(list);
        return libraries;
    }

}
