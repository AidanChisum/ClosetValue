package com.example.closetvalue;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// Serves as an extra layer of abstraction between the database and the viewmodel, such that if
// data comes in from other sources like the internet, the viewmodel doesn't need to know or care
public class GarmentRepository {
    private GarmentDao garmentDao;
    private LiveData<List<Garment>> allGarments;

    public GarmentRepository (Application application){
        GarmentDatabase database = GarmentDatabase.getInstance(application);
        garmentDao = database.garmentDao();
        allGarments = garmentDao.getAllGarments();
    }

    // API that repository exposes to outside.
    public void insert(Garment garment){
        new InsertGarmentAsyncTask(garmentDao).execute(garment);
    }

    public void update(Garment garment){
        new UpdateGarmentAsyncTask(garmentDao).execute(garment);
    }

    public void delete(Garment garment){
        new DeleteGarmentAsyncTask(garmentDao).execute(garment);
    }

    public void deleteAllGarments(){
        new DeleteAllGarmentsAsyncTask(garmentDao).execute();
    }

    public LiveData<List<Garment>> getAllGarments() {
        return allGarments;
    }

    // AsyncTasks to do db operations since they're not allowed on main thread
    private static class InsertGarmentAsyncTask extends AsyncTask<Garment, Void, Void> {

        private GarmentDao garmentDao;

        private InsertGarmentAsyncTask(GarmentDao garmentDao){
            this.garmentDao = garmentDao;
        }

        @Override
        protected Void doInBackground(Garment... garments) {
            garmentDao.insert(garments[0]);
            return null;
        }
    }

    private static class UpdateGarmentAsyncTask extends AsyncTask<Garment, Void, Void> {

        private GarmentDao garmentDao;

        private UpdateGarmentAsyncTask(GarmentDao garmentDao){
            this.garmentDao = garmentDao;
        }

        @Override
        protected Void doInBackground(Garment... garments) {
            garmentDao.update(garments[0]);
            return null;
        }
    }

    private static class DeleteGarmentAsyncTask extends AsyncTask<Garment, Void, Void> {

        private GarmentDao garmentDao;

        private DeleteGarmentAsyncTask(GarmentDao garmentDao){
            this.garmentDao = garmentDao;
        }

        @Override
        protected Void doInBackground(Garment... garments) {
            garmentDao.delete(garments[0]);
            return null;
        }
    }

    private static class DeleteAllGarmentsAsyncTask extends AsyncTask<Void, Void, Void> {

        private GarmentDao garmentDao;

        private DeleteAllGarmentsAsyncTask(GarmentDao garmentDao){
            this.garmentDao = garmentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            garmentDao.deleteAll();
            return null;
        }
    }
}
