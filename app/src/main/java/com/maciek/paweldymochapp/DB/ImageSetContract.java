package com.maciek.paweldymochapp.DB;

import android.provider.BaseColumns;

/**
 * Created by Geezy on 01.06.2018.
 */

public class ImageSetContract {

    private ImageSetContract(){

    }

    public static  class ImageSetEntry implements BaseColumns {
        public static final String TABLE_NAME = "imageSet";
        public static final String COLUMN_TITLE = "image_name";
        public static final String COULMN_IMAGESET = "image_data";
    }

}
