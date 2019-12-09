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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class DeprecatedAdapter extends RecyclerView.Adapter {

    Cursor mCursor;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ArrayList<TextView> textViews = new ArrayList<>();

        public MyViewHolder(CardView v) {
            super(v);
            cardView = v;

            //cards can be garment representations or be notifications or other types
            switch(v.getTag().toString()){
                case "CardViewGarment":
                    TableLayout tl = (TableLayout) v.getChildAt(0);
                    TableRow tr = (TableRow) tl.getChildAt(0);
                    textViews.set(0, (TextView) tr.getChildAt(0));
                    textViews.set(1, (TextView) tr.getChildAt(1));
                    textViews.set(2, (TextView) tr.getChildAt(2));
                    break;
                case "notification":
                    ConstraintLayout cl = (ConstraintLayout) v.getChildAt(0);
                    textViews.set(0, (TextView) cl.getChildAt(0));
                    textViews.set(1, (TextView) cl.getChildAt(1));
            }
        }
    }

    public DeprecatedAdapter(Context context, Cursor cursor) {

    }

    // Create new views (invoked by the layout manager)
    @Override
    public DeprecatedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        MyViewHolder holder = (MyViewHolder) viewHolder;

        //TODO: add support for putting notifications in the view. See if there's a way to add to the top and move everything else down

        mCursor.moveToPosition(position);

        String garmentName = mCursor.getString(mCursor.getColumnIndex(ClosetContract.Garment.COLUMN_NAME_NAME));
        String garmentColor = mCursor.getString(mCursor.getColumnIndex(ClosetContract.Garment.COLUMN_NAME_COLOR));
        String garmentUses = mCursor.getString(mCursor.getColumnIndex(ClosetContract.Garment.COLUMN_NAME_USES));

        holder.textViews.get(0).setText(garmentName);
        holder.textViews.get(1).setText(garmentColor);
        holder.textViews.get(2).setText(garmentUses);

        holder.cardView.setTag(mCursor.getInt(mCursor.getColumnIndex(ClosetContract.Garment._ID)));
    }

    // Return the size of your dataset (invoked by the layout manager)
    //TODO maybe find some way to make this actually work? replaced value with -1
    @Override
    public int getItemCount() {
        return -1;
    }
}
