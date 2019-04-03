package com.example.datasell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.web3j.protocol.Web3j;

import java.math.BigInteger;

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
    private TextView amountField;
    private Button placOrderButton;
    private Button solveRequestButton;


    private boolean isSellable;
    private String address;
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
    private String uri;
    private BigInteger weiPrice;
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
        amountField = (TextView) findViewById(R.id.amountOfDataDetail);
        amountField.setVisibility(View.GONE);
        placOrderButton= (Button) findViewById(R.id.buyOfferButton);
        solveRequestButton= (Button) findViewById(R.id.solveRequestButton);

        solveRequestButton.setVisibility(View.GONE);

        isSellable = Boolean.parseBoolean(getIntent().getStringExtra("isSellable"));
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
        weiPrice = BigInteger.valueOf(Long.parseLong(totalPrice));
        privacyValueString  = getIntent().getStringExtra("privacyValue");
        anonymityConfig  = getIntent().getStringExtra("anonymityValue");
        try {
            address = getIntent().getStringExtra("address");
        }catch (Exception e){

        }
        try {
            uri = getIntent().getStringExtra("blockchainURI");
        }catch (Exception e){

        }

        try {
            String amountOfData = getIntent().getStringExtra("amountOfData");
            String price = getIntent().getStringExtra("expectedPrice");
            if(!amountOfData.equals("") && !price.equals("")){
                amountField.setVisibility(View.VISIBLE);
                amountField.setText("Amount of Data: "+amountOfData);
                solveRequestButton.setVisibility(View.VISIBLE);
                placOrderButton.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Log.i("Error_LOG", e.getMessage());
        }


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

    public void onMakeBuyOffer(View v){
        /*
        boolean success = false;
        if(!uri.equals("") && !address.equals("")){
            Web3j web3j = BlockchainManager.connectToEthereumTestnet(uri);
            Deal deal = BlockchainManager.loadDeal(address,web3j,BlockchainManager.getBuyerCredentials());
           success = BlockchainManager.buyOffer(deal,weiPrice);
           try {
               Log.i("LOG_TAG",deal.getBuyer().sendAsync().get());
           }catch (Exception e){

           }

           //TODO continue here


        }
        if(success){
            Log.i("LOG_TAG","success");
        } */
        Intent intent = new Intent(this, Bids.class);
        intent.putExtra("address",address);
        intent.putExtra("blockchainURI",uri);
        startActivity(intent);
    }

}
