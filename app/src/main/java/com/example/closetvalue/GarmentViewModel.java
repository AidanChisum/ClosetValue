package com.example.closetvalue;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GarmentViewModel extends AndroidViewModel {
    private GarmentRepository repository;
    private LiveData<List<Garment>> allGarments;

    public GarmentViewModel(@NonNull Application application) {
        super(application);
        repository = new GarmentRepository(application);
        allGarments = repository.getAllGarments();
    }

    public void insert(Garment garment) {
        repository.insert(garment);
    }

    public void update(Garment garment) {
        repository.update(garment);
    }

    public void delete(Garment garment) {
        repository.delete(garment);
    }

    public void deleteAllGarments() {
        repository.deleteAllGarments();
    }

    public LiveData<List<Garment>> getAllNotes() {
        return allGarments;
    }
}
