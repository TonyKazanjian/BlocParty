package api.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import api.model.database.DatabaseOpenHelper;
import api.model.database.table.PostTable;
import io.bloc.android.blocparty.BlocPartyApplication;
import io.bloc.android.blocparty.BuildConfig;

/**
 * Created by tonyk_000 on 7/26/2015.
 */
public class DataSource {

    public static interface Callback<Result>{
    public void onSuccess(Result result);
    public void onError(String errorMessage);
}

    private DatabaseOpenHelper databaseOpenHelper;
    private PostTable postTable;
    private List<ImagePost> imagePosts;

    public DataSource(){
        postTable = new PostTable();
        databaseOpenHelper = new DatabaseOpenHelper(BlocPartyApplication.getSharedInstance(),postTable);
        imagePosts = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (BuildConfig.DEBUG && false)
                    BlocPartyApplication.getSharedInstance().deleteDatabase("blocparty_db");
            }
            SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        }).start();

    }
}
