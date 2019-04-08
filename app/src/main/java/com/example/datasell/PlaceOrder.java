package com.example.datasell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;
import java.util.Calendar;

public class PlaceOrder extends AppCompatActivity {


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
    private TextView estimatedPlacePrice;
    private TextView privacyValue;
    private Button placOrderButton;
    private Button placeRequestButton;
    private SQLiteDatabaseHandlerGPS db;

    private boolean isOffer;
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
    private String amountOfData;
    private String expectedPrice;
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
        placeRequestButton = (Button) findViewById(R.id.placeRequestOnBlockchainButton);
        privacyValue = (TextView)findViewById(R.id.privacyValueString);

        db = new SQLiteDatabaseHandlerGPS(this);
        placOrderButton.setVisibility(View.GONE);
        placeRequestButton.setVisibility(View.GONE);

        isOffer = Boolean.parseBoolean(getIntent().getStringExtra("isOffer"));
        dataType = getIntent().getStringExtra("dataType");
        gender = getIntent().getStringExtra("gender");
        education = getIntent().getStringExtra("educationString");
        startDate = getIntent().getStringExtra("startDataDate");
        endDate = getIntent().getStringExtra("stopDataDate");
        validUntilDate = getIntent().getStringExtra("validUntil");
        metaData= getIntent().getStringExtra("metaDate");
        gatekeeper = getIntent().getStringExtra("gatekeeperIP");
        totalPrice = getIntent().getStringExtra("totalPrice");
        uri= getIntent().getStringExtra("blockchainURI");
        privacyValueString  = getIntent().getStringExtra("privacyValue");
        anonymityConfig  = getIntent().getStringExtra("anonymityValue");
        exchangeRate= getIntent().getStringExtra("exchangeRate");
        Log.i("LOG_TAG",String.valueOf(isOffer));
        if(isOffer){
            age = getAgeFromBirthYear(getIntent().getStringExtra("birthyear"));
            estimatedPrice= getIntent().getStringExtra("estimatedTransactionCosts");


            dataTypeField.setText("Sell: " + dataType );
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
            initButton(placOrderButton);
        }else{
            age =getIntent().getStringExtra("age");
            amountOfData =getIntent().getStringExtra("amountOfData");
            expectedPrice =getIntent().getStringExtra("expectedPrice");

            dataTypeField.setText("Request: " + dataType );
            birthyearField.setText("Age (Group): " + age );
            genderField.setText("Gender: "  + gender );
            educationField.setText("Education: " + education );
            startField.setText("Request Data From: " + startDate);
            stopField.setText("Request Data To: " + endDate );
            valideField.setText("Offer Valid Until: " + validUntilDate );
            metadataField.setText("Further Information: " + metaData);
            privacyValue.setText("Risk: "+ anonymityConfig+ " and Max. Privacy Value: " + privacyValueString);
            gatekeeperField.setText("Your Gatekeeper: " + gatekeeper);
            priceField.setText("Amount of Data: "+ amountOfData );
            double price = Double.parseDouble(expectedPrice) * Double.parseDouble(amountOfData);
            estimatedPlacePrice.setText("Expected Price: (€) " + price );
            initButton(placeRequestButton);
        }

    }


    private boolean initButton(Button button){
        web3j = BlockchainManager.connectToEthereumTestnet(uri);
        if(web3j != null){
            button.setVisibility(View.VISIBLE);
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
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String address = BlockchainManager.deployDeal(web3j,BlockchainManager.getCredentialsFromPrivateKey(  sharedPreferences.getString("privatekey", "")),BlockchainManager.getADDRESSBOOK(),jsonObj.toString(), BigInteger.valueOf((long)wei));
            Toast.makeText(getApplicationContext(),"Your Offer is at Block" + address,Toast.LENGTH_LONG).show();
            db.addOffer(address);
            Log.i("JSON",address);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("blockchainURI",uri);
            startActivity(intent);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }
    public void onPlaceRequest(View v){
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("isOffer","false"); // Set the first name/pair
            jsonObj.put("dataType",dataType); // Set the first name/pair
            jsonObj.put("age",age); // Set the first name/pair
            jsonObj.put("amountOfData",amountOfData);
            double price = Double.parseDouble(expectedPrice) * Double.parseDouble(amountOfData);
            jsonObj.put("expectedPrice",price);
            jsonObj.put("gender",gender); // Set the first name/pair
            jsonObj.put("education",education); // Set the first name/pair
            jsonObj.put("startDate",startDate); // Set the first name/pair
            jsonObj.put("endDate",endDate); // Set the first name/pair
            jsonObj.put("validUntilDate",validUntilDate); // Set the first name/pair
            jsonObj.put("metaData",metaData); // Set the first name/pair
            jsonObj.put("anonymity",anonymityConfig); // Set the first name/pair
            jsonObj.put("privacyValue",privacyValueString); // Set the first name/pair
            jsonObj.put("gatekeeper",gatekeeper); // Set the first name/pair

            double exchange = Double.parseDouble(exchangeRate);
            double value = price / exchange;
            double wei =  Math.floor(value * 1000000000);
            wei = wei * 1000000000;
            Log.i("JSON",String.valueOf(wei));
            Log.i("JSON",jsonObj.toString());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String address = BlockchainManager.deployDeal(web3j,BlockchainManager.getCredentialsFromPrivateKey(sharedPreferences.getString("privatekey", "")),BlockchainManager.getADDRESSBOOK(),jsonObj.toString(), BigInteger.valueOf((long)wei));
            Toast.makeText(getApplicationContext(),"Your Offer is at Block" + address,Toast.LENGTH_LONG).show();
            db.addRequest(address);
            Log.i("JSON",address);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("blockchainURI",uri);
            startActivity(intent);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }
}
