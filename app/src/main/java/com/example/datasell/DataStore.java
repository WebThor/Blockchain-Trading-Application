package com.example.datasell;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Date;

public class DataStore extends AppCompatActivity {
    private Switch switchGPS;
    private TextView gpsText;
    private SeekBar gpsSpeed;
    private BroadcastReceiver broadcastReceiver;
    private static final String LOGTAG = "GPS_LOG";
    private int progress = 0;
    private SQLiteDatabaseHandlerGPS db;
    private Date date;
    private long ts ;

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ts = date.getTime();
                    String timestamp = String.valueOf(ts);

                    Log.i(LOGTAG,"\n" + timestamp + " "+intent.getExtras().get("GPS_longitude") + " " + intent.getExtras().get("GPS_latitude"));
                    db.addGPSPosition(timestamp,intent.getExtras().get("GPS_longitude").toString(),intent.getExtras().get("GPS_latitude").toString());



                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    protected void onPause(){
        super.onPause();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_store);
        switchGPS = findViewById(R.id.storeGPSSwitch);
        gpsText = findViewById(R.id.gpsSpeedTextField);
        gpsSpeed = findViewById(R.id.gpsSeekBar);
        db = new SQLiteDatabaseHandlerGPS(this);
        gpsText.setText("Log every: " + progress + " Seconds");
        date= new Date();

        this.gpsSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                gpsText.setText("Log every: " + progress + " Seconds");



            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(LOGTAG, "Started tracking seekbar");

                if(switchGPS.isChecked()){
                    switchGPS.setChecked(false);
                }



            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                gpsText.setText("Log every: " + progress + " Seconds");
                Log.i(LOGTAG, "Stopped tracking seekbar");

                if(!switchGPS.isChecked()){
                    switchGPS.setChecked(true);
                }

            }
        });

        if(!runtime_permissions())
            enable_buttons();

    }


    private void enable_buttons() {

        switchGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                    i.putExtra("GPS_Frequence",progress);
                   startService(i);
                }else{
                    Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                    stopService(i);
                   // Log.i(LOGTAG,db.allGPSPositionsInDateRange("1553126400","1553212800").toString());
                }
            }
        });
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }

}


