package api.model.database.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tonyk_000 on 7/23/2015.
 */
public abstract class Table {

    public static interface Builder {
        public long insert(SQLiteDatabase writableDB);
    }

    protected static final String COLUMN_ID = "id"; // each table must possess an ID column

    public abstract String getName();

    public abstract String getCreateStatement();

    public void onUpgrade (SQLiteDatabase writableDatabase, int oldVersion, int newVersion){
        //do nothing
    }

    public Cursor fetchRow(SQLiteDatabase readOnlyDatabase, long rowId) {
        return readOnlyDatabase.query(true,getName(), null, COLUMN_ID + " = ?",
                new String[] {String.valueOf(rowId)}, null, null, null, null);
    }

    public static long getRowId(Cursor cursor) {
        return getLong(cursor, COLUMN_ID);
    }

    protected static String getString(Cursor cursor, String column) {
        int columnIndex = cursor.getColumnIndex(column);
        if (columnIndex ==-1) {
            return "";
        }
        return cursor.getString(columnIndex);
    }

    protected static long getLong(Cursor cursor, String column){
        int columnIndex = cursor.getColumnIndex(column);
        if (columnIndex == -1){
            return -1;
        }
        return cursor.getLong(columnIndex);
    }

    protected static boolean getBoolean(Cursor cursor, String column){
        return getLong(cursor, column) ==1;
    }
}
