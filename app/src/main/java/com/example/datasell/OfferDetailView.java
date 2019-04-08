package com.example.datasell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;

public class OfferDetailView extends AppCompatActivity {

    private String uri;
    private String buyerAddress;
    private String contractAddress;
    private String data;
    private TextView buyerNameTextView;
    private TextView buyerEmailTextView;
    private TextView buyerAddressTextView;
    private TextView buyerCompanyTextView;
    private TextView buyerCountryTextView;
    private TextView buyerPurposeTextView;
    private TextView checkbox1TextView;
    private TextView checkbox2TextView;
    private TextView checkbox3TextView;
    private TextView otherPrivacyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail_view);

       data =  getIntent().getStringExtra("data");
       buyerAddress =  getIntent().getStringExtra("buyerAddress");
       uri  =  getIntent().getStringExtra("blockchainURI");
        contractAddress =  getIntent().getStringExtra("contractAddress");



        buyerNameTextView = (TextView) findViewById(R.id.buyerNameTextView);
        buyerEmailTextView= (TextView) findViewById(R.id.buyerEmailTextView);
        buyerAddressTextView= (TextView) findViewById(R.id.buyerAddressTextView);
        buyerCompanyTextView= (TextView) findViewById(R.id.buyerCompanyTextView);
        buyerCountryTextView= (TextView) findViewById(R.id.buyerCountryTextView);
        buyerPurposeTextView= (TextView) findViewById(R.id.buyerPurposeTextView);
        checkbox1TextView= (TextView) findViewById(R.id.checkbox1TextView);
        checkbox2TextView= (TextView) findViewById(R.id.checkbox2TextView);
        checkbox3TextView = (TextView) findViewById(R.id.checkbox3TextView);
        otherPrivacyTextView= (TextView) findViewById(R.id.otherPrivacyTextView);

        try {
            JSONObject json = new JSONObject(data);
            buyerNameTextView.setText("Buyer Name: "+ json.getString("name"));
            buyerEmailTextView.setText("Buyer Email: " + json.getString("mail"));
            buyerAddressTextView.setText("Buyer Address: " + json.getString("address"));
            buyerCompanyTextView.setText("Buyer Company: " + json.getString("company"));
            buyerCountryTextView.setText("Buyer Country: " + json.getString("country"));
            buyerPurposeTextView.setText("Buying Purpose: " + json.getString("purpose"));
            checkbox1TextView.setText("Using Governmental Privacy: " + json.getString("checkbox1"));
            checkbox2TextView.setText("Using Company Privacy: " + json.getString("checkbox2"));
            if(Boolean.parseBoolean(json.getString("checkbox3"))){
                checkbox3TextView.setText("Using other Privacy");
                otherPrivacyTextView.setText("Other Privacy: " +  json.getString("additionalText"));
            }


        }catch (Exception e){

        }


    }

    public void onAcceptOffer(View v){
        Web3j web3j = BlockchainManager.connectToEthereumTestnet(uri);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Deal deal = BlockchainManager.loadDeal(contractAddress,web3j,BlockchainManager.getCredentialsFromPrivateKey(sharedPreferences.getString("privatekey", "")));
        BlockchainManager.setAllowedBidder(buyerAddress,deal);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("blockchainURI",uri);
        startActivity(i);
    }
}
