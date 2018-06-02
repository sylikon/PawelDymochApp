package com.maciek.paweldymochapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maciek.paweldymochapp.DB.ImageSetContract;

/**
 * Created by Geezy on 26.05.2018.
 */

public class ImagesetListAdapter extends RecyclerView.Adapter<ImagesetListAdapter.ViewHolder> {


    private Context mContext;
    private Cursor mCursor;
    final private ListItemClickListener mOnClickListener;

    public ImagesetListAdapter(Context context,Cursor cursor, ListItemClickListener listener) {
        this.mContext = context;
        this.mCursor = cursor;
        mOnClickListener = listener;
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextViewDefinition;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.list_item_number) ;
            mTextViewDefinition = v.findViewById(R.id.lis_item_definition);
            itemView.setOnClickListener(this);
        }
        void bind (int listIndex){
            mTextView.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View view) {
            int clickedPostion = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPostion);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)


    // Create new views (invoked by the layout manager)
    @Override
    public ImagesetListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position))
            return;
        String imageTitle = mCursor.getString(mCursor.getColumnIndex(ImageSetContract.ImageSetEntry.COLUMN_TITLE));
        String imageSet = mCursor.getString(mCursor.getColumnIndex(ImageSetContract.ImageSetEntry.COULMN_IMAGESET));
        holder.mTextView.setText(imageTitle);
        holder.mTextViewDefinition.setText(imageSet);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

}