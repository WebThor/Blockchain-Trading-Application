package com.example.datasell;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.datasell.GPS_Service_Package.GPSData;

public class SQLiteDatabaseHandlerGPS extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    //DB values
    private static final String DATABASE_NAME = "DataSellerDB";

    //GPS table and values
    private static final String TABLE_NAME_GPS = "GPS_Table";
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";

    //Offer table and values
    private static final String TABLE_NAME_OFFERS = "Offers_Table";

    //User table and values
    private static final String TABLE_NAME_USER = "USER_Table";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ISGPSCOLLECTING = "isCollectingGPS";
    private static final String KEY_ISAPPSCOLLECTING = "isCollectingApps";




    private static final String LOG_TAG = "DB_LOG";


    public SQLiteDatabaseHandlerGPS(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE_GPS = "CREATE TABLE GPS_Table ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "timestamp TEXT, "
                + "longitude TEXT, " + "latitude TEXT )";

        String CREATION_TABLE_OFFERS = "CREATE TABLE Offers_Table ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +"ownerAddress TEXT," + "contractAddress TEXT, "
                + "addressbookAddress TEXT ,"+ "isOffer INTEGER, " + "metaData TEXT, " + "ageGroup TEXT, " + "gender TEXT, " +"education TEXT, " + "fromDate TEXT, " + "toDate TEXT, "
                + "price TEXT, " + "typeOfData TEXT, "+ "gateKeeper TEXT )";

        String CREATION_TABLE_USER = "CREATE TABLE USER_Table ( "
                + "address TEXT PRIMARY KEY, "
                + "isCollectingGPS INTEGER, " + "isCollectingApps INTEGER )";

        db.execSQL(CREATION_TABLE_GPS);
        db.execSQL(CREATION_TABLE_OFFERS);
        db.execSQL(CREATION_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GPS);
        this.onCreate(db);
    }

    public List<GPSData> allGPSPositionsInDateRange(String fromDateString, String toDateString) {

        List<GPSData> gpsPositions = new LinkedList<GPSData>();
        String query = "SELECT  * FROM " + TABLE_NAME_GPS + " WHERE timestamp BETWEEN " + fromDateString + " AND " + toDateString ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                GPSData gpsData = new GPSData();
                gpsData.setTime(cursor.getString(1));
                gpsData.setLongitude(cursor.getString(2));
                gpsData.setLatitude(cursor.getString(3));

                gpsPositions.add(gpsData);

            } while (cursor.moveToNext());
        }

        return gpsPositions;
    }

    public boolean hasGPSData(){
        String query = "SELECT  * FROM " + TABLE_NAME_GPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext()){
            return true;
        }else {
            return false;
        }
    }


    public void addGPSPosition(String timestamp,String longitude, String latitude ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP,timestamp);
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_LATITUDE, latitude);
        // insert
        db.insert(TABLE_NAME_GPS,null, values);
        db.close();
    }


    public void addUser(String address){
        address = tailorID(address);
        Cursor c = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
                ContentValues values = new ContentValues();
                values.put(KEY_ADDRESS,address);
                values.put(KEY_ISGPSCOLLECTING,0);
                values.put(KEY_ISAPPSCOLLECTING,0);
                db.insert(TABLE_NAME_USER,null, values);
                db.close();
        }
        finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public void updateUserCollectings(String address, int gps, int apps){
        address = tailorID(address);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_ISGPSCOLLECTING, gps);
        newValues.put(KEY_ISAPPSCOLLECTING, apps);
        String[] args = new String[]{address};
        db.update(TABLE_NAME_USER, newValues, "address=?", args);
    }

    public User getUser(String address){
        address = tailorID(address);
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from USER_Table where address = ?";
        c = db.rawQuery(query, new String[] {address});
        if (c.moveToFirst()) {
               return new User(c.getString(0),c.getInt(1),c.getInt(2));

        }else {
            return new User("",0,0);
        }
    }



    private String tailorID(String address){
        if (address.length() > 10)        {
            return address.substring(address.length() - 10);
        }
        else        {
            return address;
        }
    }


}
