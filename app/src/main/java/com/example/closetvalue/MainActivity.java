package com.example.closetvalue;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ClosetDB dbHelper = new ClosetDB(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //query
        Cursor cursor = db.query(
                "Closet" + " Integer, primary",
                null,
                null,
                null,
                null,
                null,
                null
        );

    }

    public long addTop(String name , int uses , double price, String color,
                           String size, String notes, String sleeveLength, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("id", (String) null);
        values.put("name", name);
        values.put("type", String.valueOf(GarmentTypes.TOP));
        values.put("uses", uses);
        values.put("price", price);
        values.put("color", color);
        values.put("size", size);
        values.put("notes", notes);
        values.put("sleeve_length", sleeveLength);
        //values.put("leg_length", (String) null);


        long newRowID = db.insert("Closet", null, values);
        return newRowID;
    }

    public long addBottom(String name , int uses , double price, String color,
                       String size, String notes, String legLength, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("id", (String) null);
        values.put("name", name);
        values.put("type", String.valueOf(GarmentTypes.BOTTOM));
        values.put("uses", uses);
        values.put("price", price);
        values.put("color", color);
        values.put("size", size);
        values.put("notes", notes);
        values.put("leg_length", legLength);
        //values.put("sleeve_length", (String) null);


        long newRowID = db.insert("Closet", null, values);
        return newRowID;
    }

    public long addShoes(String name , int uses , double price, String color,
                          String size, String notes, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("id", (String) null);
        values.put("name", name);
        values.put("type", String.valueOf(GarmentTypes.SHOES));
        values.put("uses", uses);
        values.put("price", price);
        values.put("color", color);
        values.put("size", size);
        values.put("notes", notes);
        //values.put("leg_length", (String) null);


        long newRowID = db.insert("Closet", null, values);
        return newRowID;
    }

    public long addOther(String name , int uses , double price, String color,
                          String size, String notes, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("id", (String) null);
        values.put("name", name);
        values.put("type", String.valueOf(GarmentTypes.OTHER));
        values.put("uses", uses);
        values.put("price", price);
        values.put("color", color);
        values.put("size", size);
        values.put("notes", notes);

        long newRowID = db.insert("Closet", null, values);
        return newRowID;
    }
}
