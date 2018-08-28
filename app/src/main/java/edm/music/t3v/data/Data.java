package edm.music.t3v.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import edm.music.t3v.model.Danhdau;


public class Data {
    private static final String DB_NAME = "DB_FAVORITE";
    private static final int DB_VER = 1;
    private static final String TAB_AC = "FAVORITE";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LINK = "link";
    private static final String COLUMN_VIEW = "view";
    private static final String COLUMN_POSTER = "poster";
    private static Context context;
    private List<Danhdau> bmList = new ArrayList<Danhdau>();
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public Data(Context c) {
        Data.context = c;
    }

    public Data open() throws SQLDataException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        openHelper.close();
    }

    public long createData(String idp, String name, String link, String view, String poster) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, idp);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LINK, link);
        cv.put(COLUMN_VIEW, view);
        cv.put(COLUMN_POSTER, poster);
        return db.insert(TAB_AC, null, cv);

    }

    public void DELETE_Item(String id) {
        db.delete(TAB_AC, COLUMN_ID + " = " + id, null);
    }

    public List<Danhdau> getData() {
        String columns[] = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_LINK, COLUMN_VIEW, COLUMN_POSTER};
        Cursor c = db.query(TAB_AC, columns, null, null, null, null, null);
        int iRow = c.getColumnIndex(COLUMN_ID);
        String name = c.getString(1);
        String link = c.getString(2);
        String view = c.getString(3);
        String poster = c.getString(4);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Danhdau bm = new Danhdau();
            bm.setId(c.getInt(iRow));
            bm.setName(name);
            bm.setLink(link);
            bm.setView(view);
            bm.setPoster(poster);

            bmList.add(bm);


        }
        c.close();

        return bmList;
    }

    public static boolean CheckIsDataAlreadyInDBorNot(String dbfield) {
        String Query = "Select * from " + TAB_AC + " where " + COLUMN_ID + " = " + dbfield;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
