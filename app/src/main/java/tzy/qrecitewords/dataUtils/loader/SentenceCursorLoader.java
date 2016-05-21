package tzy.qrecitewords.dataUtils.loader;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;

import java.sql.Date;

import tzy.qrecitewords.dataUtils.serivce.MissionService;
import tzy.qrecitewords.dataUtils.serivce.SentenceService;

/**
 * Created by tzy on 2016/5/15.
 */
public class SentenceCursorLoader extends CursorLoader {

    public SentenceCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = SentenceService.getSentenceCursor(new Date(System.currentTimeMillis()));
        return SentenceService.getSentenceCursor(new Date(System.currentTimeMillis()));
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }
}
