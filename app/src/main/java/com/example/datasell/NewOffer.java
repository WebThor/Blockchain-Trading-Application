package com.example.datasell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewOffer extends AppCompatActivity {

    private static final String LOGTAG = "SeekBarDemo";
    private SeekBar seekBar;
    private TextView privacyTextView;
    private TextView priveField;
    private Spinner genderSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        seekBar = (SeekBar) findViewById(R.id.seekBarView );
        privacyTextView = (TextView) findViewById(R.id.currentPrivacyValueField);
        priveField = (TextView) findViewById(R.id.priceField);
        genderSpinner = (Spinner) findViewById(R.id.genderField);



        List<String> list = new ArrayList<String>();
        list.add("gender");
        list.add("male");
        list.add("female");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(dataAdapter);


        this.privacyTextView.setText("PrivacyValue: " + seekBar.getProgress() + "/" + seekBar.getMax());
        this.priveField.setText("20");

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                privacyTextView.setText("Progress: " + progressValue + "/" + seekBar.getMax());
                Log.i(LOGTAG, "Changing seekbar's progress");

            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(LOGTAG, "Started tracking seekbar");

            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                privacyTextView.setText("Progress: " + progress + "/" + seekBar.getMax());
                Log.i(LOGTAG, "Stopped tracking seekbar");

            }
        });

    }
}
