package tzy.qrecitewords.dataUtils.words_utils;

/**
 * Created by tzy on 2016/5/1.
 */
public class TableInfo {
    public static class Table_Word{
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME = "table_word";
        //单词 String word;
        public static final String column_word = "word";
        //音标 String phonogram;
        public static final String column_phonogram = "phonogram";
        //中文解释 String paraphrase;
        public static final String column_paraphrase = "paraphrase";
        //熟悉程度 0代表熟悉；1代表不熟悉；2代表很不熟悉，<0代表没读过int familiarity;
        public static final String column_familiarity = "familiarity";
        //最后阅读时间 long lastReadTime ;
        public static final String column_lastReadTime = "lastReadTime";
    }

    public static class Table_Library{
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME = "table_library";
        //词库名称
        public static final String column_libraryName = "column_libraryName";
        ///**0代表不存在，1代表存在*/
        public static final String column_isExist = "column_isExist";

        public static final String column_createdTime = "column_createdTime";
    }

    public static class Table_Sentence{
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME = "table_Sentence";

        public static final String column_content = "column_content";

        public static final String column_date = "column_date";
    }
}
