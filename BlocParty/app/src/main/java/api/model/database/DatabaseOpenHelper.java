package api.model.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import api.model.database.table.Table;

/**
 * Created by tonyk_000 on 7/23/2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    //helper class: for creating and updating databases

    private static final String NAME = "blocparty_db"; //need to designate db by name

    private static final int VERSION =1;

    private Table[] tables;

    public DatabaseOpenHelper(Context context, Table... tables) {
        super(context, NAME, null, VERSION); //pass version and name of database to super constructor (SQLiteOpenHelper). This compares info from each version, and if the info differs, SQLiteOpenHelper updates the db
        this.tables = tables;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //iterating over table objects and executing their onCreate statements
            for (Table table : tables) {
                db.execSQL(table.getCreateStatement());
            }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (Table table: tables){
                table.onUpgrade(db, oldVersion, newVersion);
            }
        }
}
