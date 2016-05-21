package tzy.qrecitewords.dataUtils.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import java.sql.Date;

import tzy.qrecitewords.dataUtils.serivce.MissionService;

/**
 * Created by tzy on 2016/5/15.
 */
public class MissionDailyCursorLoader extends CursorLoader {
    public MissionDailyCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return MissionService.queryTodayMissionC(new Date(System.currentTimeMillis()));
    }
}
