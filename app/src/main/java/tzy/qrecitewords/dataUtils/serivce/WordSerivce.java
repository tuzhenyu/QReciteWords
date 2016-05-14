package tzy.qrecitewords.dataUtils.serivce;

import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.raizlabs.android.dbflow.annotation.provider.Notify;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.sql.SQLDataException;
import java.util.List;

import tzy.qrecitewords.dataUtils.dbutils.ResultLisenter;
import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.LibraryInfo;
import tzy.qrecitewords.javabean.Word;
import tzy.qrecitewords.javabean.Word_Table;

/**
 * Created by tzy on 2016/5/7.
 */
public class WordSerivce {

    /**更改单词熟悉度
     * @param word 单词
     * @param toFamility 改为该熟悉度
     * */
    public static boolean changeFamility(final Word word, final int toFamility){

        Boolean trTag = false;
        ITransaction iTransaction = new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                int oldFam = word.getFamiliarity();
                int newFam = toFamility;
                word.setFamiliarity(newFam);

                Library library = word.getLibrary();
                library.changeFamility(oldFam,newFam);

                word.setLibrary(library);

                word.update();
            }
        };
        DatabaseWrapper wrapper = FlowManager.getDatabase(WordDataBase.class).getWritableDatabase();
        try {
            wrapper.beginTransaction();
            iTransaction.execute(wrapper);
            wrapper.setTransactionSuccessful();
            trTag = true;
        }catch (Exception e){
            trTag = false;
        }
        finally {
            wrapper.endTransaction();
        }

        return trTag;
    }

    public static ITransaction insertNewLibraryITransaction(final LibraryInfo info, final Library library){

        return  new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                if(info.getWords() == null || info.getWords().size() <= 0){
                    throw new IllegalArgumentException("该词库没有单词");
                }
                library.setNull();
                library.setCountNoRead(info.getWords().size());
                library.setIsExist(Library.IsExist.exist);
                library.setSelected(false);
                if(library.getLibraryName() != null){
                    library.update();
                }else{
                    library.insert();
                }

                List<Word> words = info.getWords();
                for(Word word: words){
                    word.setLibrary(library);
                    word.insert();
                }
            }
        };
    }

    public static class NoWordsThrowable extends Throwable{
        public NoWordsThrowable(String detailMessage) {
            super(detailMessage);
        }
    }

    public static void queryWordsFromLibrary(final Library library, QueryTransaction.QueryResultCallback<Word> resultCallback){
        SQLite.select()
                .from(Word.class)
                .where(Word_Table.library_libraryName.eq(library.getLibraryName()))
                .orderBy(OrderBy.fromString("LOWER(" + "word" + ") ASC"))
                .async()
                .queryResultCallback(resultCallback)
                .execute();
    }
}
