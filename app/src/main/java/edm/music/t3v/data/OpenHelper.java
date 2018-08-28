package edm.music.t3v.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "DB_FAVORITE";
    private static final  int DB_VER = 1;
    private static final String TAB_AC = "FAVORITE";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LINK = "link";
    private static final String COLUMN_VIEW = "view";
    private static final String COLUMN_POSTER = "poster";

    public OpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TAB_AC+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+" TEXT,"+COLUMN_LINK+" TEXT,"+COLUMN_VIEW+" TEXT,"+COLUMN_POSTER+" TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TAB_AC);
        onCreate(db);
    }
}
