package com.maciek.paweldymochapp.DB;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geezy on 01.06.2018.
 */

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db) {
        if (db == null) {
            return;
        }
        //test recycle viewer, populate with fake data when both values where string
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(ImageSetContract.ImageSetEntry.COLUMN_TITLE, "John");
        cv.put(ImageSetContract.ImageSetEntry.COULMN_IMAGESET, "test5");
        list.add(cv);

        cv = new ContentValues();
        cv.put(ImageSetContract.ImageSetEntry.COLUMN_TITLE, "Tim");
        cv.put(ImageSetContract.ImageSetEntry.COULMN_IMAGESET, "test3");
        list.add(cv);

        cv = new ContentValues();
        cv.put(ImageSetContract.ImageSetEntry.COLUMN_TITLE, "Jessica");
        cv.put(ImageSetContract.ImageSetEntry.COULMN_IMAGESET, "test1");
        list.add(cv);

        cv = new ContentValues();
        cv.put(ImageSetContract.ImageSetEntry.COLUMN_TITLE, "Larry");
        cv.put(ImageSetContract.ImageSetEntry.COULMN_IMAGESET, "test4");
        list.add(cv);

        cv = new ContentValues();
        cv.put(ImageSetContract.ImageSetEntry.COLUMN_TITLE, "Kim");
        cv.put(ImageSetContract.ImageSetEntry.COULMN_IMAGESET, "test9");
        list.add(cv);

        //insert all guests in one transaction
        try {
            db.beginTransaction();
            //clear the table first
            db.delete(ImageSetContract.ImageSetEntry.TABLE_NAME, null, null);
            //go through the list and add one by one
            for (ContentValues c : list) {
                db.insert(ImageSetContract.ImageSetEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //too bad :(
        } finally {
            db.endTransaction();
        }

    }
}
