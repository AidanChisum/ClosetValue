package com.example.closetvalue;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GarmentInfoActivity extends AppCompatActivity {

    private TextView garmentInfoName;
    private TextView garmentInfoType;
    private TextView garmentInfoUses;
    private TextView garmentInfoPrice;
    private TextView garmentInfoColor;
    private TextView garmentInfoSize;
    private TextView garmentInfoNotes;

    public static final String EXTRA_ID =
            "com.example.closetvalue.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.closetvalue.EXTRA_NAME";
    public static final String EXTRA_TYPE =
            "com.example.closetvalue.EXTRA_DESCRIPTION";
    public static final String EXTRA_USES =
            "com.example.closetvalue.EXTRA_USES";
    public static final String EXTRA_PRICE =
            "com.example.closetvalue.EXTRA_PRICE";
    public static final String EXTRA_COLOR =
            "com.example.closetvalue.EXTRA_COLOR";
    public static final String EXTRA_SIZE =
            "com.example.closetvalue.EXTRA_SIZE";
    public static final String EXTRA_NOTES =
            "com.example.closetvalue.EXTRA_NOTES";

    public Intent intent;
    public Garment garment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garment_info);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        intent = getIntent();

        setTitle("[" + intent.getIntExtra(EXTRA_ID, -1) + "] Garment Info");

        garmentInfoName = findViewById(R.id.garmentInfoName);
        garmentInfoType = findViewById(R.id.garmentInfoType);
        garmentInfoUses = findViewById(R.id.garmentInfoUses);
        garmentInfoPrice = findViewById(R.id.garmentInfoPrice);
        garmentInfoColor = findViewById(R.id.garmentInfoColor);
        garmentInfoSize = findViewById(R.id.garmentInfoSize);
        garmentInfoNotes = findViewById(R.id.garmentInfoNotes);

        garmentInfoName.setText(intent.getStringExtra(EXTRA_NAME));
        garmentInfoType.setText(intent.getStringExtra(EXTRA_TYPE));
        garmentInfoUses.setText(String.valueOf(intent.getIntExtra(EXTRA_USES, 0)));
        garmentInfoPrice.setText(String.valueOf(intent.getDoubleExtra(EXTRA_PRICE, 0.0)));
        garmentInfoColor.setText(intent.getStringExtra(EXTRA_COLOR));
        garmentInfoSize.setText(intent.getStringExtra((EXTRA_SIZE)));
        garmentInfoNotes.setText(intent.getStringExtra(EXTRA_NOTES));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.garment_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_from_info:
                Intent intentPassthrough = new Intent(GarmentInfoActivity.this, AddEditGarmentActivity.class);
                intentPassthrough.putExtra(Intent.EXTRA_INTENT, intent);
                startActivityForResult(intentPassthrough, MainActivity.EDIT_GARMENT_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.EDIT_GARMENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EXTRA_ID, -1);
            setTitle("[" + data.getIntExtra(EXTRA_ID, -1) + "] Garment Info");

//            if (id == -1) {
//                Toast.makeText(this, "Garment can't be updated", Toast.LENGTH_SHORT).show();
//                return;
//            }

            String title = data.getStringExtra(AddEditGarmentActivity.EXTRA_NAME);
            String description = data.getStringExtra(AddEditGarmentActivity.EXTRA_TYPE);
            int uses = data.getIntExtra(AddEditGarmentActivity.EXTRA_USES, 0);
            Double price = data.getDoubleExtra(AddEditGarmentActivity.EXTRA_PRICE, 0.0);
            String color = data.getStringExtra(AddEditGarmentActivity.EXTRA_COLOR);
            String size = data.getStringExtra(AddEditGarmentActivity.EXTRA_SIZE);
            String notes = data.getStringExtra(AddEditGarmentActivity.EXTRA_NOTES);

            garmentInfoName.setText(title);
            garmentInfoType.setText(description);
            garmentInfoUses.setText(String.valueOf(uses));
            garmentInfoPrice.setText(String.valueOf(price));
            garmentInfoColor.setText(color);
            garmentInfoSize.setText(size);
            garmentInfoNotes.setText(notes);

            this.intent = data;

            garment = new Garment(title, description, uses, price, color, size, notes);

            garment.setId(id);
            //garmentViewModel.update(garment);super.onBackPressed();

            Toast.makeText(this, "Garment updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Garment not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent(GarmentInfoActivity.this, MainActivity.class);
        int resultCode;
        if (garment != null) {
            resultCode = MainActivity.RESULT_OK;
        } else {
            resultCode = MainActivity.RESULT_CANCELED;
        }
        data.putExtra("RETURNED_GARMENT", garment);
        setResult(resultCode, data);
        super.onBackPressed();
    }
}
