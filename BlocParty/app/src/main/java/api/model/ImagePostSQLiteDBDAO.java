package api.model;

import android.database.sqlite.SQLiteDatabase;

import api.model.database.DatabaseOpenHelper;
import api.model.database.table.PostTable;
import io.bloc.android.blocparty.BlocPartyApplication;

/**
 * Created by tonyk_000 on 7/29/2015.
 */
public class ImagePostSQLiteDBDAO implements ImagePostDAO {

    DatabaseOpenHelper databaseOpenHelper;
    PostTable postTable;
    ImagePostSQLiteDBDAO() {
        databaseOpenHelper = new DatabaseOpenHelper(BlocPartyApplication.getSharedInstance(), postTable);
        postTable = new PostTable();
    }

    @Override
    public ImagePost create(ImagePost transferObject) {

        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
//        new PostTable.Builder()
//                .setURL(transferObject.getImageURL())
//                .setImageName("AndroidCentral - Android News, Tips, and stuff!")
//                .insert(writableDatabase);
        return null;
    }

    @Override
    public boolean update(ImagePost transferObject) {
        return false;
    }

    @Override
    public void read(ImagePostListener listener) {

    }

    

    @Override
    public boolean del() {
        return false;
    }
}
