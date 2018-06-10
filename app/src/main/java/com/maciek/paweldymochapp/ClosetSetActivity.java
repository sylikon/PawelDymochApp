package com.maciek.paweldymochapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.maciek.paweldymochapp.DB.ImageSetContract;
import com.maciek.paweldymochapp.DB.ImageSetDbQuery;
import com.maciek.paweldymochapp.utilities.DbBitmapUtility;
import com.maciek.paweldymochapp.DB.ImageSetDbHelper;
import com.maciek.paweldymochapp.DB.InsertSet;
import com.maciek.paweldymochapp.utilities.PreparePictureUtility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.maciek.paweldymochapp.utilities.PermissionUtility.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;
import static com.maciek.paweldymochapp.utilities.PermissionUtility.checkPermissionWRITE_EXTERNAL_STORAGE;

public class ClosetSetActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private Button saveSetButton;
    private ImageView mImageView;
    private Bitmap dispachedPhoto;
    private EditText editText;
    SQLiteDatabase db;
    ImageSetDbQuery imageSetDbQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_set);


        mImageView = findViewById(R.id.set_image_view);
        saveSetButton = findViewById(R.id.save_set_button);
        saveSetButton.setOnClickListener(this);
        checkPermissionWRITE_EXTERNAL_STORAGE(this);
        ImageSetDbHelper dbHelper = new ImageSetDbHelper(this);
        db = dbHelper.getReadableDatabase();
        db = dbHelper.getWritableDatabase();
        imageSetDbQuery = new ImageSetDbQuery(db);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String title = bundle.getString("IMAGE_TITLE");
            Toast.makeText(this, bundle.getString("IMAGE_TITLE"), Toast.LENGTH_SHORT).show();bundle.getString("IMAGE_TITLE");
            new LoadImageViewFromDb().execute(title);

        }


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });







    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = PreparePictureUtility.createImageFile(this);
                mCurrentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.maciek.paweldymochapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            dispachedPhoto = PreparePictureUtility.preparePicture(mImageView,mCurrentPhotoPath);
            mImageView.setImageBitmap(dispachedPhoto);
            galleryAddPic();
        }
    }

    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.save_set_button) :
                Toast.makeText(this, "dodano zdjecie do setu", Toast.LENGTH_SHORT).show();
                editText = findViewById(R.id.set_name_editText);
                String title = editText.getText().toString();
                InsertSet.insertImage(db,title, DbBitmapUtility.getBytes(dispachedPhoto));
                MediaStore.Images.Media.insertImage(getContentResolver(), dispachedPhoto, title , "test");
//                addImageView();

        }
    }

    public void addImageView(){
        LinearLayout linearLayout = findViewById(R.id.inner_layout);
        ImageView image = new ImageView(ClosetSetActivity.this);
//        android:layout_width="150dp"
//        android:layout_height="250dp"
//        android:src="@mipmap/ic_launcher"
//        android:id="@+id/set_image_view"
//        android:layout_gravity="center"
        image.setBackgroundResource(R.drawable.ic_launcher_background);
        linearLayout.addView(image,150,250);

    }



    private class LoadImageViewFromDb extends AsyncTask<String, Void, byte[]> {

        @Override
        protected byte[] doInBackground(String... titles) {
            int count = titles.length;
            for(int i = 0; i<count; i++){
                Cursor cursor = imageSetDbQuery.getQueriedImageSet(titles[i]);
                while (cursor.moveToPosition(i)){
                    byte[] image = cursor.getBlob(1);
                    return image;
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            mImageView.setImageBitmap(DbBitmapUtility.getImage(bytes));
        }
    }




}
