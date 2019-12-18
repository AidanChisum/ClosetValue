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
    public static final int EDIT_GARMENT_REQUEST = 2;

    private GarmentViewModel garmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddGarment = findViewById(R.id.button_add_garment);
        buttonAddGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditGarmentActivity.class);
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

        adapter.setOnItemClickListener(new GarmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Garment garment) {
                Intent intent = new Intent(MainActivity.this, AddEditGarmentActivity.class);
                intent.putExtra(AddEditGarmentActivity.EXTRA_ID, garment.getId());
                intent.putExtra(AddEditGarmentActivity.EXTRA_NAME, garment.getName());
                intent.putExtra(AddEditGarmentActivity.EXTRA_TYPE, garment.getType());
                intent.putExtra(AddEditGarmentActivity.EXTRA_USES, garment.getUses());
                intent.putExtra(AddEditGarmentActivity.EXTRA_PRICE, garment.getPrice());
                intent.putExtra(AddEditGarmentActivity.EXTRA_COLOR, garment.getColor());
                intent.putExtra(AddEditGarmentActivity.EXTRA_SIZE, garment.getSize());
                intent.putExtra(AddEditGarmentActivity.EXTRA_NOTES, garment.getNotes());
                startActivityForResult(intent, EDIT_GARMENT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GARMENT_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditGarmentActivity.EXTRA_NAME);
            String description = data.getStringExtra(AddEditGarmentActivity.EXTRA_TYPE);
            int uses = data.getIntExtra(AddEditGarmentActivity.EXTRA_USES, 0);
            String price = data.getStringExtra(AddEditGarmentActivity.EXTRA_PRICE);
            String color = data.getStringExtra(AddEditGarmentActivity.EXTRA_COLOR);
            String size = data.getStringExtra(AddEditGarmentActivity.EXTRA_SIZE);
            String notes = data.getStringExtra(AddEditGarmentActivity.EXTRA_NOTES);

            Garment garment;

            if (price.isEmpty()) {
                garment = new Garment(title, description, uses, 0.0, color, size, notes);
            } else {
                garment = new Garment(title, description, uses, Double.valueOf(price), color, size, notes);
            }

            garmentViewModel.insert(garment);

            Toast.makeText(this, "Garment saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_GARMENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditGarmentActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Garment can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditGarmentActivity.EXTRA_NAME);
            String description = data.getStringExtra(AddEditGarmentActivity.EXTRA_TYPE);
            int uses = data.getIntExtra(AddEditGarmentActivity.EXTRA_USES, 0);
            String price = data.getStringExtra(AddEditGarmentActivity.EXTRA_PRICE);
            String color = data.getStringExtra(AddEditGarmentActivity.EXTRA_COLOR);
            String size = data.getStringExtra(AddEditGarmentActivity.EXTRA_SIZE);
            String notes = data.getStringExtra(AddEditGarmentActivity.EXTRA_NOTES);

            Garment garment;

            if (price.isEmpty()) {
                garment = new Garment(title, description, uses, 0.0, color, size, notes);
            } else {
                garment = new Garment(title, description, uses, Double.valueOf(price), color, size, notes);
            }

            garment.setId(id);
            garmentViewModel.update(garment);

            Toast.makeText(this, "Garment updated", Toast.LENGTH_SHORT).show();
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