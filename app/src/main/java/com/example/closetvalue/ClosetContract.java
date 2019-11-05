package com.example.closetvalue;

import android.provider.BaseColumns;

public class ClosetContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ClosetContract() {}

    /* Inner class that defines the table contents */
    public static class Garment implements BaseColumns {
        public static final String TABLE_NAME = "garments";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_USES = "uses";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_SLEEVE_LENGTH = "sleeve_length";
        public static final String COLUMN_NAME_LEG_LENGTH = "leg_length";
    }
}
