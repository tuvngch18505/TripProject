package com.example.tripproject.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Create Constance
    private static final String DATABASE_NAME = "expenseManager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "trips";

    private static final String ID_COLUMN = "trip_id";
    private static final String NAME_COLUMN = "name";
    private static final String DESTINATION_COLUMN = "destination";
    private static final String DAY_COLUMN = "day";
    private static final String RISK_COLUMN = "risk";
    private static final String NOTE_COLUMN = "note";
    private static final String SERVICE_COLUMN = "service";

    public static final String NEW_TRIP_ID = "0";

    private SQLiteDatabase database;
    private  DatabaseHelper databaseHelper;

    // Query Create Table
    private static final String CREATE_TRIP_TABLE = String.format(
            "CREATE TABLE %s (" +
                    " %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT)", TABLE_NAME, ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, DAY_COLUMN, RISK_COLUMN, NOTE_COLUMN, SERVICE_COLUMN
    );


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TRIP_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TRIPS_TABLE = String.format("DROP TABLE IF EXISTS", TABLE_NAME);
        db.execSQL(DROP_TRIPS_TABLE);

        Log.v(this.getClass().getName(), DATABASE_NAME + "Database upgrade to version " + newVersion + " - old data lost");
        if (newVersion > oldVersion) {
            onCreate(db);
        }
    }

    //Insert trips
    public void addTrip(TripEntity trip) {

        ContentValues values = new ContentValues(); // ContentValues đại diện cho một hàng trong bảng dưới dạng bản đồ khóa / giá tri

        values.put(NAME_COLUMN, trip.getNameTrip());
        values.put(DESTINATION_COLUMN, trip.getDestination());
        values.put(DAY_COLUMN, trip.getDayOfTrip());
        values.put(RISK_COLUMN, trip.isRiskEvaluate());
        values.put(NOTE_COLUMN, trip.getNote());
        values.put(SERVICE_COLUMN, trip.getChooseServices());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }


    public TripEntity getTripById(int tripID) {
        Cursor cursor = database.query(TABLE_NAME, null, ID_COLUMN + " = ?", new String[]{String.valueOf(tripID)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        TripEntity trip = new TripEntity(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6));
        return trip;
    }

    // add method getTrip();
    // getAllTrips
    public List<TripEntity> getAllTrips() {
        List<TripEntity> tripList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            TripEntity trip = new TripEntity(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6));
            tripList.add(trip);
            cursor.moveToNext();
        }
        return tripList;
    }

    public void updateTrip(TripEntity trip) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, trip.getNameTrip());
        values.put(DESTINATION_COLUMN, trip.getDestination());
        values.put(DAY_COLUMN, trip.getDayOfTrip());
        values.put(RISK_COLUMN, trip.isRiskEvaluate());
        values.put(NOTE_COLUMN, trip.getNote());
        values.put(SERVICE_COLUMN, trip.getChooseServices());

        db.update(TABLE_NAME, values, ID_COLUMN + " = ?", new String[]{String.valueOf(trip.getTripId())});
        db.close();

    }

    public void deleteId(int tripId){
        database.delete(TABLE_NAME, ID_COLUMN + " = ?", new String[] {String.valueOf(tripId)});
        database.close();
    }

}
