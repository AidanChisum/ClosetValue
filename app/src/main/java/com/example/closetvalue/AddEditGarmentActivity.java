package com.example.closetvalue;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditGarmentActivity extends AppCompatActivity {
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

    private EditText editTextName;
    private EditText editTextType;
    private NumberPicker numberPickerUses;
    private EditText editTextPrice;
    private EditText editTextColor;
    private EditText editTextSize;
    private EditText editTextNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garment);

        editTextName = findViewById(R.id.edit_text_name);
        editTextType = findViewById(R.id.edit_text_type);
        numberPickerUses = findViewById(R.id.number_picker_uses);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextColor = findViewById(R.id.edit_text_color);
        editTextSize = findViewById(R.id.edit_text_size);
        editTextNotes = findViewById(R.id.edit_text_notes);

        numberPickerUses.setMinValue(0);
        numberPickerUses.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Garment");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextType.setText(intent.getStringExtra(EXTRA_TYPE));
            numberPickerUses.setValue(intent.getIntExtra(EXTRA_USES, 1));
            editTextPrice.setText(String.valueOf(intent.getDoubleExtra(EXTRA_PRICE, 0.0)));
            editTextColor.setText(intent.getStringExtra(EXTRA_COLOR));
            editTextSize.setText(intent.getStringExtra((EXTRA_SIZE)));
            editTextNotes.setText(intent.getStringExtra(EXTRA_NOTES));
        } else {
            setTitle("Add Garment");
        }
    }

    private void saveGarment() {
        String name = editTextName.getText().toString();
        String type = editTextType.getText().toString();
        int uses = numberPickerUses.getValue();
        String price = editTextPrice.getText().toString();
        String color = editTextColor.getText().toString();
        String size = editTextSize.getText().toString();
        String notes = editTextNotes.getText().toString();


        if (name.trim().isEmpty() || type.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name and type", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_USES, uses);
        data.putExtra(EXTRA_PRICE, price);
        data.putExtra(EXTRA_COLOR, color);
        data.putExtra(EXTRA_SIZE, size);
        data.putExtra(EXTRA_NOTES, notes);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_garment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_garment:
                saveGarment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}