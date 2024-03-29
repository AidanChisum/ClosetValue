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
    public static final int GARMENT_RETURNED = 3;

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
                if (direction == ItemTouchHelper.RIGHT) {
                    garmentViewModel.delete(adapter.getGarmentAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Garment deleted", Toast.LENGTH_SHORT).show();
                } else if (direction == ItemTouchHelper.LEFT) {
                    Garment garment = adapter.getGarmentAt(viewHolder.getAdapterPosition());
                    garment.incrementUses();
                    garmentViewModel.update(garment);
                    Toast.makeText(MainActivity.this, "Added use for garment '" + garment.getName() + "'", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new GarmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Garment garment) {
                Intent intent = new Intent(MainActivity.this, GarmentInfoActivity.class);
                intent.putExtra(GarmentInfoActivity.EXTRA_ID, garment.getId());
                intent.putExtra(GarmentInfoActivity.EXTRA_NAME, garment.getName());
                intent.putExtra(GarmentInfoActivity.EXTRA_TYPE, garment.getType());
                intent.putExtra(GarmentInfoActivity.EXTRA_USES, garment.getUses());
                intent.putExtra(GarmentInfoActivity.EXTRA_PRICE, garment.getPrice());
                intent.putExtra(GarmentInfoActivity.EXTRA_COLOR, garment.getColor());
                intent.putExtra(GarmentInfoActivity.EXTRA_SIZE, garment.getSize());
                intent.putExtra(GarmentInfoActivity.EXTRA_NOTES, garment.getNotes());
                startActivityForResult(intent, GARMENT_RETURNED);
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
            double price = data.getDoubleExtra(AddEditGarmentActivity.EXTRA_PRICE, 0.0);
            String color = data.getStringExtra(AddEditGarmentActivity.EXTRA_COLOR);
            String size = data.getStringExtra(AddEditGarmentActivity.EXTRA_SIZE);
            String notes = data.getStringExtra(AddEditGarmentActivity.EXTRA_NOTES);

            Garment garment;

            if (Double.isNaN(price)) {
                garment = new Garment(title, description, uses, 0.0, color, size, notes);
            } else {
                garment = new Garment(title, description, uses, price, color, size, notes);
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
        } else if (requestCode == GARMENT_RETURNED && resultCode == RESULT_OK) {
            Garment garment = (Garment) data.getExtras().getSerializable("RETURNED_GARMENT");
            garmentViewModel.update(garment);
            Toast.makeText(this, "[" + garment.getId() + "] Garment " + garment.getName() + " with uses " + garment.getUses(), Toast.LENGTH_SHORT).show();
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