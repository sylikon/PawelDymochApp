package com.maciek.paweldymochapp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Geezy on 01.06.2018.
 */

public class ImageSetDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ImageSet.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ImageSetContract.ImageSetEntry.TABLE_NAME + " (" +
                    ImageSetContract.ImageSetEntry._ID + " INTEGER PRIMARY KEY," +
                    ImageSetContract.ImageSetEntry.COLUMN_TITLE + " TEXT," +
                    ImageSetContract.ImageSetEntry.COULMN_IMAGESET + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ImageSetContract.ImageSetEntry.TABLE_NAME;




    public ImageSetDbHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
