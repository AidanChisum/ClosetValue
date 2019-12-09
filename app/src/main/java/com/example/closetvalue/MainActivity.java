package com.example.closetvalue;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GarmentViewModel garmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GarmentAdapter adapter = new GarmentAdapter();
        recyclerView.setAdapter(adapter);

        garmentViewModel = ViewModelProviders.of(this).get(GarmentViewModel.class);
        garmentViewModel.getAllGarments().observe(this, new Observer<List<Garment>>() {
            @Override
            public void onChanged(@Nullable List<Garment> garments) {
                adapter.setGarments(garments);
            }
        });

    }

}
