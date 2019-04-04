package com.example.datasell;

import java.util.LinkedList;
import java.util.List;
import java.util.SimpleTimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.datasell.GPS_Service_Package.GPSData;

import org.web3j.tuples.generated.Tuple2;

public class SQLiteDatabaseHandlerGPS extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 5;
    //DB values
    private static final String DATABASE_NAME = "DataSellerDB";

    //GPS table and values
    private static final String TABLE_NAME_GPS = "GPS_Table";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";

    //Offer table and values
    private static final String TABLE_NAME_OFFERS = "Offers_Table";
    private static final String KEY_OWNERADDRESS = "ownerAddress";

    //Request table value
    private static final String TABLE_NAME_REQUEST= "Request_Table";

    //Bid table value
    private static final String TABLE_NAME_BID= "Bid_Table";
    private static final String KEY_CONTRACTADDRESS= "contractAddress";
    private static final String KEY_BIDDATA = "data";

    //Purches table value
    private static final String TABLE_NAME_PURCHASE= "Purchase_Table";




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
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +"ownerAddress TEXT)";

        String CREATION_TABLE_REQUESTS = "CREATE TABLE Request_Table ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +"ownerAddress TEXT)";

        String CREATION_TABLE_BIDS = "CREATE TABLE Bid_Table ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +"contractAddress TEXT, " + "data TEXT)";

        String CREATION_TABLE_PURCHASE = "CREATE TABLE Purchase_Table ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +"contractAddress TEXT )";

        String CREATION_TABLE_USER = "CREATE TABLE USER_Table ( "
                + "address TEXT PRIMARY KEY, "
                + "isCollectingGPS INTEGER, " + "isCollectingApps INTEGER )";

        db.execSQL(CREATION_TABLE_GPS);
        db.execSQL(CREATION_TABLE_OFFERS);
        db.execSQL(CREATION_TABLE_REQUESTS);
        db.execSQL(CREATION_TABLE_BIDS);
        db.execSQL(CREATION_TABLE_PURCHASE);
        db.execSQL(CREATION_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_OFFERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PURCHASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        this.onCreate(db);
    }

    public List<GPSData> getAllGPSPositionsInDateRange(String fromDateString, String toDateString) {
        //Adjust different time zones
        long to = Long.parseLong(toDateString);
        to = to + 80000000;
        toDateString = String.valueOf(to);
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

    public List<GPSData> getAllGPSPositionsAfter(String fromDateString){
        List<GPSData> gpsPositions = new LinkedList<GPSData>();
        String query = "SELECT  * FROM " + TABLE_NAME_GPS + " WHERE timestamp > " + fromDateString ;
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

    public List<GPSData> getAllGPSPositions(){
        List<GPSData> gpsPositions = new LinkedList<GPSData>();
        String query = "SELECT  * FROM " + TABLE_NAME_GPS;
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


    public void addPurchase(String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT  * FROM " + TABLE_NAME_PURCHASE + " WHERE " + "contractAddress" + " LIKE " + "'%" +tailorID(address) + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            values.put(KEY_CONTRACTADDRESS,address);
            db.insert(TABLE_NAME_PURCHASE,null, values);
        }
        db.close();
    }

    public List<String> getAllPurchases(){
       List<String> bids = new LinkedList<String>();
        String query = "SELECT  * FROM " + TABLE_NAME_PURCHASE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                bids.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return bids;
    }




    public void addBid(String address, String data ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT  * FROM " + TABLE_NAME_BID + " WHERE " + "contractAddress" + " LIKE " + "'%" +tailorID(address) + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            values.put(KEY_CONTRACTADDRESS,address);
            values.put(KEY_BIDDATA,data);
            db.insert(TABLE_NAME_BID,null, values);
        }
        db.close();
    }

    public boolean removeBid(String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT  * FROM " + TABLE_NAME_BID + " WHERE " + "contractAddress" + " LIKE " + "'%" +tailorID(address) + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            int index = cursor.getInt(0);
            return db.delete(TABLE_NAME_BID, "id" + "=?", new String[]{String.valueOf(index)}) > 0;
        }
        return false;
    }

    public List<Tuple2<String,String>> getAllBids(){
       List<Tuple2<String,String>> bids = new LinkedList<Tuple2<String,String>>();
        String query = "SELECT  * FROM " + TABLE_NAME_BID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                bids.add(new Tuple2<>(cursor.getString(1),cursor.getString(2)));

            } while (cursor.moveToNext());
        }

        return bids;
    }


    public void addOffer(String address ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OWNERADDRESS,address);
        // insert
        db.insert(TABLE_NAME_OFFERS,null, values);
        db.close();
    }




    public List<String> getAllOfferAddresses(){
        List<String> offers = new LinkedList<String>();
        String query = "SELECT  * FROM " + TABLE_NAME_OFFERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String offer = cursor.getString(1);
                offers.add(offer);

            } while (cursor.moveToNext());
        }

        return offers;
    }


    public void addRequest(String address ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OWNERADDRESS,address);
        // insert
        db.insert(TABLE_NAME_REQUEST,null, values);
        db.close();
    }


    public List<String> getAllRequestAddresses(){
        List<String> request = new LinkedList<String>();
        String query = "SELECT  * FROM " + "Request_Table";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String offer = cursor.getString(1);
                request.add(offer);

            } while (cursor.moveToNext());
        }

        return request;
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
