package com.example.datasell;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;

public class Bids extends AppCompatActivity {


    private EditText buyerName;
    private EditText buyerEmail;
    private EditText buyerAddress;
    private EditText buyerCompany;
    private EditText buyerCountry;
    private EditText buyerPurpose;
    private RadioButton enablePrivacy;
    private LinearLayout additonalPrivacyLayout;
    private CheckBox boxCountry;
    private CheckBox boxCompany;
    private CheckBox boxOther;
    private EditText privacyOther;
    private Button submitButton;
    private boolean isChecked = false;
    private String address;
    private String uri;
    private SQLiteDatabaseHandlerGPS db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);
        address = getIntent().getStringExtra("address");
        uri = getIntent().getStringExtra("blockchainURI");
        db = new SQLiteDatabaseHandlerGPS(this);
        initLayout();
        addListenerOnButton();

    }

    private void initLayout(){
        buyerName = (EditText)findViewById(R.id.buyerNameField);
        buyerEmail = (EditText)findViewById(R.id.buyerMmail);
        buyerAddress = (EditText)findViewById(R.id.buyerAddress);
        buyerCompany =  (EditText)findViewById(R.id.buyerCompany);
        buyerCountry = (EditText)findViewById(R.id.buyerCountry);
        buyerPurpose  = (EditText)findViewById(R.id.purposeForBuying);
        enablePrivacy = (RadioButton) findViewById(R.id.radioEnableMorePrivacy);
        additonalPrivacyLayout = (LinearLayout) findViewById(R.id.additionalPrivacyLayout);
        boxCountry = (CheckBox)findViewById(R.id.checkBox1);
        boxCompany  = (CheckBox)findViewById(R.id.checkBox2);
        boxOther  = (CheckBox)findViewById(R.id.checkBox3);
        privacyOther =  (EditText) findViewById(R.id.additionalPrivacyText);
        submitButton = (Button) findViewById(R.id.submitBidButton);
    }

    private void addListenerOnButton() {
        View.OnClickListener first_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                isChecked = !isChecked;
                if(isChecked){
                    additonalPrivacyLayout.setVisibility(View.VISIBLE);
                    enablePrivacy.setChecked(true);

                }else{
                    additonalPrivacyLayout.setVisibility(View.INVISIBLE);
                    enablePrivacy.setChecked(false);
                }
            }
        };
        CheckBox.OnCheckedChangeListener checkedChangeListener = new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("LOG_TAG",String.valueOf(isChecked));
                if(isChecked){
                    privacyOther.setVisibility(View.VISIBLE);
                }else{
                    privacyOther.setVisibility(View.INVISIBLE);
                }
            }
        };
        enablePrivacy.setOnClickListener(first_radio_listener);
        boxOther.setOnCheckedChangeListener(checkedChangeListener);
    }

    public void onPlaceBid(View v){
        try {
            // Here we convert Java Object to JSON
            Log.i("LOG_TAG",address);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name", buyerName.getText().toString()); // Set the first name/pair
            jsonObj.put("mail",buyerEmail.getText().toString()); // Set the first name/pair
            jsonObj.put("address",buyerAddress.getText().toString()); // Set the first name/pair
            jsonObj.put("company",buyerCompany.getText().toString());
            jsonObj.put("country",buyerCountry.getText().toString());
            jsonObj.put("purpose",buyerPurpose.getText().toString()); // Set the first name/pair
            jsonObj.put("additionalPrivacy",String.valueOf(enablePrivacy.isChecked())); // Set the first name/pair
            jsonObj.put("checkbox1",String.valueOf(boxCountry.isChecked())); // Set the first name/pair
            jsonObj.put("checkbox2",String.valueOf(boxCompany.isChecked())); // Set the first name/pair
            jsonObj.put("checkbox3",String.valueOf(boxOther.isChecked())); // Set the first name/pair
            jsonObj.put("additionalText",privacyOther.getText().toString()); // Set the first name/pair
            Web3j web3j = BlockchainManager.connectToEthereumTestnet(uri);
            Deal deal = BlockchainManager.loadDeal(address,web3j,BlockchainManager.getBuyerCredentials());
            BlockchainManager.makeBid(deal,jsonObj.toString());
            Toast.makeText(getApplicationContext(),"Your Bid is registered" ,Toast.LENGTH_SHORT).show();
            db.addBid(address,jsonObj.toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("blockchainURI",uri);
            startActivity(intent);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }
}
