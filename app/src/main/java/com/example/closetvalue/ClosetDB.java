package com.example.closetvalue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClosetDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Closet.db";
    public static final int  DATABASE_VERSION = 1;

    private static final String CREATE_CLOSET_TABLE = "create table Closet(" +
            "id Int, " +
            "name Text, " +
            "type Text, " +
            "uses Int, " +
            "price Real, " +
            "color Text, " +
            "size Text, " +
            "notes Text" +
            ")";

    public ClosetDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLOSET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
