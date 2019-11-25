package com.example.closetvalue;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Garment.class, version = 1)
public abstract class GarmentDatabase extends RoomDatabase {

    private static GarmentDatabase instance;

    public abstract GarmentDao garmentDao();

    public static synchronized GarmentDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GarmentDatabase.class, "garment_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private GarmentDao garmentDao;

        private PopulateDBAsyncTask(GarmentDatabase db){
            garmentDao = db.garmentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO: populate database using inserts.
            //garmentDao.insert(new Garment());
            return null;
        }
    }

}
