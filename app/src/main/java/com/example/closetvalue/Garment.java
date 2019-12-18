package com.example.closetvalue;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "garment_table")
public class Garment {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String type;
    private int uses;
    private double price;
    private String color;
    private String size;
    private String notes;

    // CONSTRUCTOR
    public Garment(String name, String type, int uses, double price, String color, String size, String notes) {
        this.name = name;
        this.type = type;
        this.uses = uses;
        this.price = price;
        this.color = color;
        this.size = size;
        this.notes = notes;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getUses() {
        return uses;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getNotes() {
        return notes;
    }

    public void incrementUses() {
        this.uses++;
    }
}
