package com.example.closetvalue;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GarmentViewModel garmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        garmentViewModel = ViewModelProviders.of(this).get(GarmentViewModel.class);
        garmentViewModel.getAllNotes().observe(this, new Observer<List<Garment>>() {
            @Override
            public void onChanged(@Nullable List<Garment> notes) {
                //update RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
