package com.maciek.paweldymochapp.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Geezy on 10.06.2018.
 */

public class ImageSetDbQuery {
    private SQLiteDatabase mDb;
    public ImageSetDbQuery(SQLiteDatabase db){
        mDb = db;
    }

    public Cursor getQueriedImageSet(String title){
        String[] ary = new String[] {ImageSetContract.ImageSetEntry.COLUMN_TITLE, ImageSetContract.ImageSetEntry.COULMN_IMAGESET};
        String selection = ImageSetContract.ImageSetEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = {title};
        return mDb.query(ImageSetContract.ImageSetEntry.TABLE_NAME,
                ary,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }
}
