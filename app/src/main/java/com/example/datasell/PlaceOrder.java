package com.example.datasell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlaceOrder extends AppCompatActivity {

    private LinearLayout preview;
    private TextView dataTypeField;
    private TextView  birthyearField;
    private TextView   genderField;
    private TextView   educationField;
    private TextView   startField;
    private TextView     stopField;
    private TextView   valideField;
    private TextView   metadataField;
    private TextView    gatekeeperField;
    private TextView   priceField;
    private TextView   estimatedPlacePrice;
    private String dataType;
    private String age;
    private String gender;
    private String education;
    private String startDate;
    private String endDate;
    private String validUntilDate;
    private String metaData;
    private String gatekeeper;
    private String totalPrice;
    private String estimatedPrice;
    private String exchangeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        preview = (LinearLayout)findViewById(R.id.linearLayoutPreview);
        dataTypeField = (TextView) findViewById(R.id.dataTypeField);
        birthyearField= (TextView) findViewById(R.id.birthyearField);
        genderField= (TextView) findViewById(R.id.genderField);
        educationField= (TextView) findViewById(R.id.educationField);
        startField= (TextView) findViewById(R.id.startField);
        stopField= (TextView) findViewById(R.id.stopField);
        valideField= (TextView) findViewById(R.id.valideField);
        metadataField= (TextView) findViewById(R.id.metadataField);
        gatekeeperField= (TextView) findViewById(R.id.gatekeeperField);
        priceField= (TextView) findViewById(R.id.priceField);
        estimatedPlacePrice = (TextView) findViewById(R.id.estimatedPlacePrice);

        dataType = getIntent().getStringExtra("dataType");
        age = getAgeFromBirthYear(getIntent().getStringExtra("birthyear"));
        gender = getIntent().getStringExtra("gender");
        education = getIntent().getStringExtra("educationString");
        startDate = getIntent().getStringExtra("startDataDate");
        endDate = getIntent().getStringExtra("stopDataDate");
        validUntilDate = getIntent().getStringExtra("validUntil");
        metaData= getIntent().getStringExtra("metaDate");
        gatekeeper = getIntent().getStringExtra("gatekeeperIP");
        totalPrice = getIntent().getStringExtra("totalPrice");
        estimatedPrice= getIntent().getStringExtra("estimatedTransactionCosts");
        exchangeRate= getIntent().getStringExtra("exchangeRate");

        dataTypeField.setText("Sell / Buy: " + dataType );
        birthyearField.setText("Age: " + age );
        genderField.setText("Gender: "  + gender );
        educationField.setText("Education: " + education );
        startField.setText("Sell Data From: " + startDate);
        stopField.setText("Sell Data To: " + endDate );
        valideField.setText("Offer Valid Until: " + validUntilDate );
        metadataField.setText("Further Information: " + metaData);
        gatekeeperField.setText("Your Gatekeeper: " + gatekeeper);
        priceField.setText("Total Price: (€) " + totalPrice);
        estimatedPlacePrice.setText("Offer Price ("+ exchangeRate + "/ETH) (€): " + estimatedPrice );

    }

    private String getAgeFromBirthYear(String birthYear){
        int birthYearInt = Integer.parseInt(birthYear);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        birthYearInt = year - birthYearInt;
        return String.valueOf(birthYearInt);
    }

    public void onPlaceOffer(View v){

    }
    public void onPlaceRequest(View v){

    }
}
