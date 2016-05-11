package tzy.qrecitewords.dataUtils.serivce;

import android.support.annotation.BoolRes;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.sql.SQLDataException;
import java.util.List;

import tzy.qrecitewords.dataUtils.dbutils.ResultLisenter;
import tzy.qrecitewords.dataUtils.dbutils.WordDataBase;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.LibraryInfo;
import tzy.qrecitewords.javabean.Word;

/**
 * Created by tzy on 2016/5/7.
 */
public class WordSerivce {

    /**
     * 查询词库信息
     */
    public static LibraryInfo getLibraryInfo(final Library library,final ResultLisenter<LibraryInfo> lisenter) {

        /*SQLite.select(Word_Table.familiarity, Method.count(Word_Table.id))
                .from(Word.class)
                .where(Word_Table.library_id.eq(library.getId()))
                .groupBy(Word_Table.familiarity)
                .async()
                .query(new TransactionListener<Cursor>() {
                    @Override
                    public void onResultReceived(Cursor result) {
                        LibraryInfo info = LibraryInfo.CursorToLibraryInfo(library.getIntrodu(),result);
                        lisenter.reviceResult(info);
                    }

                    @Override
                    public boolean onReady(BaseTransaction<Cursor> transaction) {
                        return transaction.onReady();
                    }

                    @Override
                    public boolean hasResult(BaseTransaction<Cursor> transaction, Cursor result) {
                        if(result != null )
                            return true;
                        else
                            return false;
                    }
                });*/

        return null;
    }

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
                library.setCountNoRead(info.getWords().size());
                library.setIsExist(Library.IsExist.exist);
                library.setSelected(false);
                library.insert();

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

}
