package com.example.datasell;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datasell.Validator_Package.Validator;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewOffer extends AppCompatActivity {

    private static final String LOGTAG = "OFFER_LOG";
    private SeekBar seekBar;
    private TextView privacyTextView;
    private TextView expectedPriceField;
    private Spinner genderSpinner;
    private TextView totalDataValueTextfield;
    private int privacyValue = 0;
    private int showProgressValue;
    private double currentPrice;
    //Fields for Input
    private TextView birthDayField;
    private TextView offerValidUntil;
    private TextView education;
    private String ownerAddress;
    private String contractAddress;
    private String addressbookAddress;
    private int isOffer;
    private TextView metaData;
    private TextView gender;
    private TextView fromData;
    private TextView toDate;
    private Spinner typeOfData;
    private Spinner riskSpinner;
    private TextView gateKeeper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        seekBar = (SeekBar) findViewById(R.id.seekBarView );
        privacyTextView = (TextView) findViewById(R.id.currentPrivacyValueField);
        expectedPriceField = (TextView) findViewById(R.id.expectedPriceField);
        genderSpinner = (Spinner) findViewById(R.id.genderField);
        typeOfData = (Spinner) findViewById(R.id.typeOfDataField);
        riskSpinner = (Spinner) findViewById(R.id.riskSpinner);
        birthDayField = (TextView) findViewById(R.id.birthField);
        totalDataValueTextfield = (TextView) findViewById(R.id.totalDataValueField);
        List<String> list = new ArrayList<String>();
        list.add("gender");
        list.add("male");
        list.add("female");
       initSpinner(genderSpinner,list);
       initSpinner(typeOfData,getListOfferableData());
       initSpinner(riskSpinner,getRiskFunctions());
       offerValidUntil = (TextView) findViewById(R.id.offerValideUntil);
        education = (TextView) findViewById(R.id.educationField);
        metaData = (TextView) findViewById(R.id.furtherMetaData);
        fromData = (TextView) findViewById(R.id.fromDateField);
        toDate = (TextView) findViewById(R.id.toDateField);
        gateKeeper = (TextView) findViewById(R.id.gatekeeperField);


        this.privacyTextView.setText("PrivacyValue: " + seekBar.getProgress() + "/" + seekBar.getMax());
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                privacyValue = seekBar.getMax() - progressValue;
                showProgressValue = progressValue;
                privacyTextView.setText("Privacy Value: " + showProgressValue );
                currentPrice = Double.parseDouble(String.format(Locale.US,"%.3g%n",getPrice()));
                String output = "Price (€): " + currentPrice;
                expectedPriceField.setText(output);
                updateTotalPrice();


            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                privacyTextView.setText("Privacy Value: " + showProgressValue );
                currentPrice = Double.parseDouble(String.format(Locale.US,"%.3g%n",getPrice()));
                String output = "Price (€): " + currentPrice;
                expectedPriceField.setText(output);
                updateTotalPrice();


            }
        });

        typeOfData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentPrice = Double.parseDouble(String.format(Locale.US,"%.3g%n",0.0));
                String output = "Price (€): " + currentPrice;
                expectedPriceField.setText(output);
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        fromData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               updateTotalPrice();
            }
        });

        toDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTotalPrice();
            }
        });

        offerValidUntil.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               updateTotalPrice();
            }
        });

    }

    private void updateTotalPrice(){
        if(Validator.isOfferCorrect(fromData.getText().toString(),toDate.getText().toString(),offerValidUntil.getText().toString())){
            totalDataValueTextfield.setText("Total: " + currentPrice);
        }
    }

    private double getPrice(){
        double price = getEstimateDataValue();
        if(riskSpinner.getSelectedItemPosition() == 0){
            price = price * getConservativeValue((double) privacyValue)*10;
        }else{
            price = price * getLiberalValue((double) privacyValue)*10;
        }
        return price;
    }




    public void onPlaceOffer(View v){
        String birthyear;
        String gender;
        String startDataDate;
        String stopDataDate;
        String validUntil;
        String metaDate = "";
        String sellPrice;
        String gatekeeperIP;



        if(Validator.verifyAgeField(birthDayField)
        && Validator.verifyGender(genderSpinner)
        && Validator.isThisDateValid(fromData.getText().toString(),"dd.mm.yyyy")
        && Validator.isThisDateValid(toDate.getText().toString(),"dd.mm.yyyy")
        && Validator.isThisDateValid(offerValidUntil.getText().toString(),"dd.mm.yyyy")
        && Validator.isDateOneBigger(offerValidUntil.getText().toString(),toDate.getText().toString())
        && Validator.validateIfIP(gateKeeper)
        && Validator.chooseRightData(typeOfData)){
            birthyear = birthDayField.getText().toString();
            gender = genderSpinner.getSelectedItem().toString();
            startDataDate = fromData.getText().toString();
            stopDataDate = toDate.getText().toString();
            validUntil = offerValidUntil.getText().toString();
            metaDate = this.metaData.getText().toString();
            gatekeeperIP = gateKeeper.getText().toString();

        }
    }

    private double getEstimateDataValue(){
        return 0.15;
    }
    private double getLiberalValue(double price){
        return (((Math.log(30) / Math.log(2) ) * Math.log(9000 * price + 1)) / 130);
    }
    private double getConservativeValue(double price){
        return ((8*price) / Math.sqrt(1100+500*price*price));
    }


    private List<String> getListOfferableData(){
        List<String> dataType = new ArrayList<String>();
        SQLiteDatabaseHandlerGPS db = new SQLiteDatabaseHandlerGPS(this);
        dataType.add("Choose Data");
        if(db.hasGPSData()){
            dataType.add("GPS Data");
        }
        return dataType;
    }

    private List<String> getRiskFunctions(){
        List<String> dataType = new ArrayList<String>();
       dataType.add("Low-Risk, Low-Return");
       dataType.add("High-Risk, High-Return");
       return dataType;
    }

    private void initSpinner(Spinner spinner, List<String> list){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

}
