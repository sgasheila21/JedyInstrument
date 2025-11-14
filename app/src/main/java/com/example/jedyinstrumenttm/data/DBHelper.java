package com.example.jedyinstrumenttm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS User (\n" +
            "\tuserID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tusername TEXT,\n" +
            "\temail TEXT,\n" +
            "\tpassword TEXT,\n" +
            "\tphoneNumber TEXT\n" +
            ")";

    private static String SQL_CREATE_INSTRUMENT_TABLE = "CREATE TABLE IF NOT EXISTS Instrument (\n" +
            "\tinstrumentID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tinstrumentName TEXT,\n" +
            "\tinstrumentRating REAL,\n" +
            "\tinstrumentPrice REAL,\n" +
            "\tinstrumentImagePath TEXT,\n" +
            "\tinstrumentDescription TEXT\n" +
            ")";

    private static String SQL_CREATE_ACTIVE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS ActiveOrder (\n" +
            "\tactiveOrderID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tuserID INTEGER,\n" +
            "\tinstrumentID INTEGER,\n" +
            "\tinstrumentQuantity INTEGER\n" +
            ")";

    public DBHelper(Context context) {
        super(context, "TEMP11", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_INSTRUMENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ACTIVE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Instrument");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ActiveOrder");
    }
}
