package tzy.qrecitewords.dataUtils.serivce;

import android.database.Cursor;

import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import tzy.qrecitewords.dataUtils.dbutils.ResultLisenter;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.LibraryInfo;
import tzy.qrecitewords.javabean.Word;
import tzy.qrecitewords.javabean.Word_Table;

/**
 * Created by tzy on 2016/5/7.
 */
public class WordSerivce {

    /**
     * 查询词库信息
     */
    public static LibraryInfo getLibraryInfo(final Library library,final ResultLisenter<LibraryInfo> lisenter) {

        SQLite.select(Word_Table.familiarity, Method.count(Word_Table.id))
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
                });

        return null;
    }
}
