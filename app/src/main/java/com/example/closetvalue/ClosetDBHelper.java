package com.example.closetvalue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public class ClosetDBHelper extends SQLiteOpenHelper {
    String TAG = "com.example.closetvalue::ClosetDBHelper";

    public static final String DATABASE_NAME = "Closet.db";
    public static final int  DATABASE_VERSION = 1;

    public ClosetDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GARMENTS_TABLE);
        Log.i(TAG, " in onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static final String CREATE_GARMENTS_TABLE =
            "CREATE TABLE " + ClosetContract.Garment.TABLE_NAME + "(" +
            ClosetContract.Garment._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ClosetContract.Garment.COLUMN_NAME_NAME +  "TEXT, " +
            ClosetContract.Garment.COLUMN_NAME_TYPE + "TEXT, " +
            ClosetContract.Garment.COLUMN_NAME_USES + "INTEGER, " +
            ClosetContract.Garment.COLUMN_NAME_PRICE + "REAL, " +
            ClosetContract.Garment.COLUMN_NAME_COLOR + "TEXT, " +
            ClosetContract.Garment.COLUMN_NAME_SIZE + "TEXT, " +
            ClosetContract.Garment.COLUMN_NAME_NOTES + "TEXT," +
            ClosetContract.Garment.COLUMN_NAME_SLEEVE_LENGTH + " Text, " +
            ClosetContract.Garment.COLUMN_NAME_LEG_LENGTH + "Text" +
            ")";
}
