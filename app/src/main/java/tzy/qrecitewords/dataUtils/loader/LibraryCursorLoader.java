package tzy.qrecitewords.dataUtils.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.raizlabs.android.dbflow.sql.language.Select;

import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.Library_Table;

/**
 * Created by tzy on 2016/5/15.
 */
public class LibraryCursorLoader extends CursorLoader {

   // ForceLoadContentObserver mObserver;

    public LibraryCursorLoader(Context context) {
        super(context);
       // mObserver = new ForceLoadContentObserver();
    }

    @Override
    public Cursor loadInBackground() {

        Cursor cursor = new Select().from(Library.class).where(Library_Table.isSelected.eq(true)).query();
       /* if (cursor != null) {
            // Ensure the cursor window is filled
            cursor.getCount();
            cursor.registerContentObserver(mObserver);
        }
        cursor.getNotificationUri();*/
        return cursor;
    }
}
