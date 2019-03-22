package com.example.datasell;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHandlerGPS extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataSellerDB";
    private static final String TABLE_NAME = "GPS_Table";
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String LOG_TAG = "DB_LOG";
    private static final String[] COLUMNS = { KEY_ID,KEY_TIMESTAMP, KEY_LONGITUDE, KEY_LATITUDE };

    public SQLiteDatabaseHandlerGPS(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE GPS_Table ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "timestamp TEXT, "
                + "longitude TEXT, " + "latitude TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public List<String> allGPSPositionsInDateRange(String fromDateString, String toDateString) {

        List<String> gpsPositions = new LinkedList<String>();
        String longi;
        String lati;
        String time;
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE timestamp BETWEEN " + fromDateString + " AND " + toDateString ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);



        if (cursor.moveToFirst()) {
            do {
                time = cursor.getString(2);
                longi = cursor.getString(3);
                lati = cursor.getString(4);

                gpsPositions.add(time + " " + longi + " " + lati);

            } while (cursor.moveToNext());
        }

        return gpsPositions;
    }

    public String getLongitude(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Log.i(LOG_TAG,cursor.getString(2));
        return cursor.getString(2);
    }

    public void addGPSPosition(String timestamp,String longitude, String latitude ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP,timestamp);
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_LATITUDE, latitude);
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }


}
