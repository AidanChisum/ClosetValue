package com.example.closetvalue;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_GARMENT_REQUEST = 1;

    private GarmentViewModel garmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddGarment = findViewById(R.id.button_add_garment);
        buttonAddGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGarmentActivity.class);
                startActivityForResult(intent, ADD_GARMENT_REQUEST);
            }
        });

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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                garmentViewModel.delete(adapter.getGarmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Garment deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GARMENT_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddGarmentActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddGarmentActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddGarmentActivity.EXTRA_PRIORITY, 1);

            Garment garment = new Garment(title, description, priority, 0.0, "", "", "");
            garmentViewModel.insert(garment);

            Toast.makeText(this, "Garment saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Garment not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_garments:
                garmentViewModel.deleteAllGarments();
                Toast.makeText(this, "All garments deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}