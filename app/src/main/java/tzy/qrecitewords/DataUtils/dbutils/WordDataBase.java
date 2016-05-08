package tzy.qrecitewords.dataUtils.dbutils;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

import tzy.qrecitewords.javabean.Library;
import tzy.qrecitewords.javabean.Library_Table;

/**
 * Created by tzy on 2016/4/8.
 */
@Database(name = WordDataBase.NAME,version = WordDataBase.VERSION,foreignKeysSupported = true)
public class WordDataBase {
    //数据库名称
    public static final String NAME = "AppDatabase";
    //数据库版本号
    public static final int VERSION = 4;

    @Migration(version = WordDataBase.VERSION,database = WordDataBase.class)
    public static  class Migration_Library extends AlterTableMigration<Library>{

        public Migration_Library(Class<Library> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            super.onPreMigrate();
            addColumn(SQLiteType.INTEGER, Library_Table.isSelected.getNameAlias().getName());
        }
    }
}