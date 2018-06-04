package com.maciek.paweldymochapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maciek.paweldymochapp.DB.ImageSetContract;
import com.maciek.paweldymochapp.DB.ImageSetDbHelper;
import com.maciek.paweldymochapp.DB.TestUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImagesetListAdapter.ListItemClickListener, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button button;
    private TextView mTextView;
    SQLiteDatabase db;

    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        button = findViewById(R.id.new_set_button);
        button.setOnClickListener(this);
        mTextView = findViewById(R.id.empty_cursor_text_view) ;

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        ImageSetDbHelper dbHelper = new ImageSetDbHelper(this);
        db = dbHelper.getReadableDatabase();

        mTextView.setText("");
        Cursor cursor = getAllImageSets();
        if(cursor.getCount()==0){
            mTextView.setText("Brak setów kliknij przycisk zeby dodac");
        }



        // specify an adapter (see also next example)
        mAdapter = new ImagesetListAdapter(this, cursor, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        String toastMessage = "Item #" + clickedItemIndex + " clicked";
        toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        toast.show();
    }

    private Cursor getAllImageSets(){
        String[] ary = new String[] {ImageSetContract.ImageSetEntry.COLUMN_TITLE};
        return db.query(ImageSetContract.ImageSetEntry.TABLE_NAME,
                    ary,
                null,
                null,
                null,
                null,
                ImageSetContract.ImageSetEntry._ID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.new_set_button) :
                startActivity(new Intent(this, ClosetSetActivity.class));

        }
    }
}
