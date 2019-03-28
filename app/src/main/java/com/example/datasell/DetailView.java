package com.example.datasell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailView extends AppCompatActivity {

    private TextView dataTypeField;
    private TextView birthyearField;
    private TextView genderField;
    private TextView educationField;
    private TextView startField;
    private TextView stopField;
    private TextView valideField;
    private TextView metadataField;
    private TextView gatekeeperField;
    private TextView priceField;
    private TextView privacyValue;
    private Button placOrderButton;

    private String isSellableString;
    private boolean isSellable;
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
    private String privacyValueString;
    private String anonymityConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        dataTypeField = (TextView) findViewById(R.id.dataTypeFieldDetail);
        birthyearField= (TextView) findViewById(R.id.birthyearFieldDetail);
        genderField= (TextView) findViewById(R.id.genderFieldDetail);
        educationField= (TextView) findViewById(R.id.educationFieldDetail);
        startField= (TextView) findViewById(R.id.startFieldDetail);
        stopField= (TextView) findViewById(R.id.stopFieldDetail);
        valideField= (TextView) findViewById(R.id.valideFieldDetail);
        metadataField= (TextView) findViewById(R.id.metadataFieldDetail);
        gatekeeperField= (TextView) findViewById(R.id.gatekeeperFieldDetail);
        priceField= (TextView) findViewById(R.id.priceFieldDetail);
        privacyValue= (TextView) findViewById(R.id.privacyValueStringDetail);
        placOrderButton= (Button) findViewById(R.id.buyOfferButton);


        isSellableString = getIntent().getStringExtra("isSellable");
        isSellable = Boolean.parseBoolean(isSellableString);
        dataType = getIntent().getStringExtra("dataType");
        age = getIntent().getStringExtra("age");
        gender = getIntent().getStringExtra("gender");
        education = getIntent().getStringExtra("educationString");
        startDate = getIntent().getStringExtra("startDataDate");
        endDate = getIntent().getStringExtra("stopDataDate");
        validUntilDate = getIntent().getStringExtra("validUntil");
        metaData= getIntent().getStringExtra("metaDate");
        gatekeeper = getIntent().getStringExtra("gatekeeperIP");
        totalPrice = getIntent().getStringExtra("totalPrice");
        privacyValueString  = getIntent().getStringExtra("privacyValue");
        anonymityConfig  = getIntent().getStringExtra("anonymityValue");

        double ether = Double.parseDouble(totalPrice) / 1000000000000000000L;

        dataTypeField.setText("Data: " + dataType );
        birthyearField.setText("Age: " + age );
        genderField.setText("Gender: "  + gender );
        educationField.setText("Education: " + education );
        startField.setText("Sell Data From: " + startDate);
        stopField.setText("Sell Data To: " + endDate );
        valideField.setText("Offer Valid Until: " + validUntilDate );
        metadataField.setText("Further Information: " + metaData);
        privacyValue.setText("Risk: "+ anonymityConfig+ " and Privacy Value: " + privacyValueString);
        gatekeeperField.setText("Gatekeeper: " + gatekeeper);
        priceField.setText("Total Price: (Ether) " + ether);

        if(isSellable){
            placOrderButton.setVisibility(View.VISIBLE);
        }else {
            placOrderButton.setVisibility(View.GONE);
        }

    }
}
