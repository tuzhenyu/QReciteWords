package tzy.qrecitewords.dataUtils.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;

import java.util.List;

import tzy.qrecitewords.dataUtils.serivce.WordSerivce;
import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.Word;

/**
 * Created by tzy on 2016/5/15.
 */
public class WordsCursorLoader extends AsyncTaskLoader<List<Word>> {

    int mFamility = 0;
    Library mLibrary ;
    int mLimit;
    public WordsCursorLoader(Context context,int famility,int limit,Library library ) {
        super(context);
        mFamility = famility;
        mLibrary = library;
        mLimit = limit;
    }


    @Override
    public List<Word> loadInBackground() {
        return WordSerivce.getWordsToRecited(mLibrary,mFamility,mLimit);
    }

    /* @Override
    public List<Word> loadInBackground() {

    }*/
}
