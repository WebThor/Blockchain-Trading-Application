package com.example.datasell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;
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
    private TextView   privacyValue;
    private Button placOrderButton;
    private Button placeRequestButton;

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
    private String privacyValueString;
    private String anonymityConfig;

    private String uri;
    private Web3j web3j;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, NewOffer.class);
        intent.putExtra("blockchainURI",uri);
        startActivity(intent);
    }


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
        placOrderButton = (Button) findViewById(R.id.placeOfferOnBlockchainButton);
        placeRequestButton = (Button) findViewById(R.id.placeplaceRequestButtonOfferButton2);
        privacyValue = (TextView)findViewById(R.id.privacyValueString);

        placOrderButton.setEnabled(false);
        placeRequestButton.setEnabled(false);

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
        uri= getIntent().getStringExtra("blockchainURI");
        privacyValueString  = getIntent().getStringExtra("privacyValue");
        anonymityConfig  = getIntent().getStringExtra("anonymityValue");

        dataTypeField.setText("Sell / Buy: " + dataType );
        birthyearField.setText("Age: " + age );
        genderField.setText("Gender: "  + gender );
        educationField.setText("Education: " + education );
        startField.setText("Sell Data From: " + startDate);
        stopField.setText("Sell Data To: " + endDate );
        valideField.setText("Offer Valid Until: " + validUntilDate );
        metadataField.setText("Further Information: " + metaData);
        privacyValue.setText("Risk: "+ anonymityConfig+ " and Privacy Value: " + privacyValueString);
        gatekeeperField.setText("Your Gatekeeper: " + gatekeeper);
        priceField.setText("Total Price: (€) " + totalPrice);
        estimatedPlacePrice.setText("Offer Price ("+ exchangeRate + "/ETH) (€): " + estimatedPrice );
        initButtons();
    }


    private boolean initButtons(){
        web3j = BlockchainManager.connectToEthereumTestnet(uri);
        if(web3j != null){
            placOrderButton.setEnabled(true);
            placeRequestButton.setEnabled(true);
            return true;
        }else{
            Toast.makeText(this,"You are not connected to a Blockchain", Toast.LENGTH_SHORT);
            return false;
        }

    }

    private String getAgeFromBirthYear(String birthYear){
        int birthYearInt = Integer.parseInt(birthYear);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        birthYearInt = year - birthYearInt;
        return String.valueOf(birthYearInt);
    }

    public void onPlaceOffer(View v){
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("isOffer","true"); // Set the first name/pair
            jsonObj.put("dataType",dataType); // Set the first name/pair
            jsonObj.put("age",age); // Set the first name/pair
            jsonObj.put("gender",gender); // Set the first name/pair
            jsonObj.put("education",education); // Set the first name/pair
            jsonObj.put("startDate",startDate); // Set the first name/pair
            jsonObj.put("endDate",endDate); // Set the first name/pair
            jsonObj.put("validUntilDate",validUntilDate); // Set the first name/pair
            jsonObj.put("metaData",metaData); // Set the first name/pair
            jsonObj.put("anonymity",anonymityConfig); // Set the first name/pair
            jsonObj.put("privacyValue",privacyValueString); // Set the first name/pair
            jsonObj.put("gatekeeper",gatekeeper); // Set the first name/pair
            double priceDouble = Double.parseDouble(totalPrice);
            double exchange = Double.parseDouble(exchangeRate);
            double value = priceDouble / exchange;
            double wei =  Math.floor(value * 1000000000);
            wei = wei * 1000000000;
            Log.i("JSON",String.valueOf(wei));
            Log.i("JSON",jsonObj.toString());
            String address = BlockchainManager.deployDeal(web3j,BlockchainManager.getCredentialsFromPrivateKey(),BlockchainManager.getADDRESSBOOK(),jsonObj.toString(), BigInteger.valueOf((long)wei));
            Toast.makeText(getApplicationContext(),"Your Offer is at Block" + address,Toast.LENGTH_LONG);
            Log.i("JSON",address);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }
    public void onPlaceRequest(View v){

    }
}
