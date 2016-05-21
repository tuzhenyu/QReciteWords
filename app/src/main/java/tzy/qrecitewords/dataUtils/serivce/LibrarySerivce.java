package tzy.qrecitewords.dataUtils.serivce;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import tzy.qrecitewords.dataUtils.SQLiteHelper;
import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.javabean.Libraries;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.Library_Table;
import tzy.qrecitewords.javabean.Word;
import tzy.qrecitewords.javabean.Word_Table;

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

    /**查询已被选中的词库*/
    public static Library getSelectedLbrary(){
       return new Select().from(Library.class).where(Library_Table.isSelected.eq(true)).querySingle();
    }

    public static boolean changLibrary(final Library olLibr, final Library newLibr){
        ITransaction iTransaction = new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                if(olLibr != null){
                    olLibr.setSelected(false);
                    olLibr.update();
                }
                newLibr.setSelected(true);
                if(newLibr.getLibraryName() != null ){
                    newLibr.update();
                }else{
                    newLibr.insert();
                }

            }
        };
        return SQLiteHelper.TransactionSubmit(iTransaction);
    }

    public static boolean deleteTableAsync(final Library library, Transaction.Success success, Transaction.Error error){
        ITransaction iTransaction = new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                String lname = library.getLibraryName();
                SQLite.delete(Word.class)
                        .where(Word_Table.library_libraryName.eq(lname))
                        .execute();
                if( !library.isCustom() ){//如果不是用户自己生产的
                    library.setNull();
                    library.update();
                }else{
                    library.delete();
                }


            }
        };

        FlowManager.getDatabase(WordDataBase.class)
                .beginTransactionAsync(iTransaction)
                .success(success)
                .error(error)
                .shouldRunInTransaction(true)
                .runCallbacksOnSameThread(false)
                .build()
                .execute();

        return true;
    }

    public static Library createCustomerLibrary(String libraryName,String introdu){
        Library library = new Library();
        library.setLibraryName(libraryName);
        library.setIntrodu(introdu);
        library.setNull();
        library.setIsExist(Library.IsExist.exist);
        library.setCustom(true);
        library.insert();

        library = new Select().from(Library.class)
                .where(Library_Table.libraryName.eq(libraryName))
                .querySingle();
        return library;
    }

    public static boolean addWord(final Library library, final Word word){

        ITransaction transaction = new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                library.setCountNoRead(library.getCountNoRead() + 1);
                word.setLibrary(library);
                word.insert();
            }
        };

        return SQLiteHelper.TransactionSubmit(transaction);
    }
}
