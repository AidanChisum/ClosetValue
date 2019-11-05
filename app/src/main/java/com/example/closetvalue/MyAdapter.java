package com.example.closetvalue;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

class MyAdapter extends RecyclerView.Adapter {

    Cursor mCursor;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textViewGarmentName;
        public TextView textViewGarmentColor;
        public TextView textViewGarmentUses;

        public MyViewHolder(CardView v) {
            super(v);
            cardView = v;
            TableLayout tl = (TableLayout) v.getChildAt(0);
            TableRow tr = (TableRow) tl.getChildAt(0);
            textViewGarmentColor = (TextView) tr.getChildAt(0);
            textViewGarmentName = (TextView) tr.getChildAt(1);
            textViewGarmentUses = (TextView) tr.getChildAt(2);
        }
    }

    public MyAdapter(Context context, Cursor cursor) {

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        //TextView v = (TextView) LayoutInflater.from(parent.getContext())
                //.inflate(R.layout.my_text_view, parent, false);

        // TODO update card view to include add-use button

        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.garment_card_view, parent, false);


        MyViewHolder vh = new MyViewHolder(cv);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //holder.textView.setText(mDataset[position]);
        //TODO: replace card values with actual garment values
        MyViewHolder holder = (MyViewHolder) viewHolder;

        mCursor.moveToPosition(position);

        String garmentName = mCursor.getString(mCursor.getColumnIndex(ClosetContract.Garment.COLUMN_NAME_NAME));
        String garmentColor = mCursor.getString(mCursor.getColumnIndex(ClosetContract.Garment.COLUMN_NAME_COLOR));
        String garmentUses = mCursor.getString(mCursor.getColumnIndex(ClosetContract.Garment.COLUMN_NAME_USES));

        holder.textViewGarmentName.setText(garmentName);
        holder.textViewGarmentColor.setText(garmentColor);
        holder.textViewGarmentUses.setText(garmentUses);

        holder.cardView.setId(mCursor.getInt(mCursor.getColumnIndex(ClosetContract.Garment._ID)));
    }

    // Return the size of your dataset (invoked by the layout manager)
    //TODO maybe find some way to make this actually work? replaced value with -1
    @Override
    public int getItemCount() {
        return -1;
    }
}
