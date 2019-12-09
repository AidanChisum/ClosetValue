package com.example.closetvalue;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GarmentDao {

    @Insert
    void insert(Garment garment);

    @Update
    void update(Garment garment);

    @Delete
    void delete(Garment garment);

    @Query("DELETE from garment_table")
    void deleteAllGarments();

    @Query("SELECT * FROM garment_table")
    LiveData<List<Garment>> getAllGarments();

}
