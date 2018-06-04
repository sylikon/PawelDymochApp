package com.maciek.paweldymochapp.DB;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geezy on 04.06.2018.
 */

public class InsertSet {


    public static void insertImage(SQLiteDatabase db, String title, byte[] image) {
        if (db == null) {
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(ImageSetContract.ImageSetEntry.COLUMN_TITLE, title);
        cv.put(ImageSetContract.ImageSetEntry.COULMN_IMAGESET, image);

        //insert all guests in one transaction
        try {
            db.beginTransaction();
            //clear the table first
            db.insert(ImageSetContract.ImageSetEntry.TABLE_NAME, null ,cv);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //too bad :(
        } finally {
            db.endTransaction();
        }

    }
}
