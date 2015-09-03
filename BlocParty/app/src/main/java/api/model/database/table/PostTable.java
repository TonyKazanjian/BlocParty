package api.model.database.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tonyk_000 on 7/26/2015.
 */
public class PostTable extends Table {

    public class Builder implements Table.Builder{

        ContentValues values = new ContentValues();

        public Builder setURL (String url){
            values.put(COLUMN_URL, url);
            return this;
        }

        public Builder setCaption(String caption){
            values.put(COLUMN_CAPTION, caption);
            return this;
        }

        public Builder setOwner (String owner){
            values.put(COLUMN_OWNER, owner);
            return this;
        }

        public Builder setDate(long date){
            values.put(COLUMN_POSTED_ON, date);
            return this;
        }

//        public Builder setImageId(long imageId){
//            values.put(COLUMN_IMAGE_ID, imageId);
//            return this;
//        }

        @Override
        public long insert(SQLiteDatabase writableDB) {
            return writableDB.insert(ID, null, values);
        }
    }

    //NAME has been replaced with ID
    public static final String ID = "image_id";
    public static final String COLUMN_URL = "image_url";
    public static final String COLUMN_CAPTION = "image_caption";
    public static final String COLUMN_OWNER = "image_owner";
    public static final String COLUMN_POSTED_ON = "date_posted";
    //public static final String COLUMN_IMAGE_ID = "image_id";



    @Override
    public String getId() {
        return "image_id";
    }

    @Override
    public String getCreateStatement() {
        return "CREATE TABLE " + getId() + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_URL + "TEXT,"
                + COLUMN_CAPTION + "TEXT,"
                + COLUMN_OWNER + "TEXT,"
                + COLUMN_POSTED_ON + "INTEGER";
    }
}
