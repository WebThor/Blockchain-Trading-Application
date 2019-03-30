package com.example.datasell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.util.List;

public class MyOffers extends AppCompatActivity {

    private SQLiteDatabaseHandlerGPS db;
    private Web3j web3j;
    private String uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);
        db = new SQLiteDatabaseHandlerGPS(this);
         List<String> myOffers = db.getAllOfferAddresses();
         List<String> myRequests = db.getAllRequestAddresses();
         for(String s : myRequests){
             myOffers.add(s);
         }
        db.close();
        uri = getIntent().getStringExtra("blockchainURI");
        Log.i("MyOffer_LOG",uri);
        web3j = BlockchainManager.connectToEthereumTestnet(uri);
        getOffers(myOffers);
    }

    public void getOffers(List<String> addresses){
        LinearLayout ll = (LinearLayout) findViewById(R.id.myOffersLinearLayout);
        Credentials creds = BlockchainManager.getCredentialsFromPrivateKey();
        ll.removeAllViews();
        for (String s : addresses){

            try{
                Deal deal = BlockchainManager.loadDeal(s,web3j,creds);
                Button b = new Button(this);
                Tuple3<String, String, BigInteger> tupel = deal.returnContractDetails().sendAsync().get();
                JSONObject data = new JSONObject(tupel.getValue2());
                boolean isOffer = Boolean.parseBoolean(data.getString("isOffer"));
                Log.i("LOG_TAG",String.valueOf(isOffer));
                if(isOffer){
                    String text = MainActivity.getInfoString(data);
                    b.setText(text);
                    if(MainActivity.isAnonymized(data.getString("anonymity"))){
                        b.setBackgroundResource(R.drawable.buyanonymizedbutton);
                        b.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(getApplicationContext(), DetailView.class);
                                    intent.putExtra("isSellable","false");
                                    intent.putExtra("dataType",data.getString("dataType"));
                                    intent.putExtra("age","");
                                    intent.putExtra("gender","");
                                    intent.putExtra("educationString","");
                                    intent.putExtra("startDataDate",data.getString("startDate"));
                                    intent.putExtra("stopDataDate", data.getString("endDate"));
                                    intent.putExtra("validUntil",data.getString("validUntilDate"));
                                    intent.putExtra("metaDate",data.getString("metaData"));
                                    intent.putExtra("gatekeeperIP",data.getString("gatekeeper"));
                                    intent.putExtra("totalPrice",tupel.getValue3().toString());
                                    intent.putExtra("privacyValue",data.getString("privacyValue"));
                                    intent.putExtra("anonymityValue",data.getString("anonymity"));
                                    startActivity(intent);
                                }catch (Exception e){
                                    Log.i("Error_LOG", e.getMessage());
                                }
                            }
                        });
                    }else{
                        b.setBackgroundResource(R.drawable.buybutton);
                        b.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(getApplicationContext(), DetailView.class);
                                    intent.putExtra("isSellable","false");
                                    intent.putExtra("dataType",data.getString("dataType"));
                                    intent.putExtra("age",data.getString("age"));
                                    intent.putExtra("gender",data.getString("gender"));
                                    intent.putExtra("educationString",data.getString("education"));
                                    intent.putExtra("startDataDate",data.getString("startDate"));
                                    intent.putExtra("stopDataDate", data.getString("endDate"));
                                    intent.putExtra("validUntil",data.getString("validUntilDate"));
                                    intent.putExtra("metaDate",data.getString("metaData"));
                                    intent.putExtra("gatekeeperIP",data.getString("gatekeeper"));
                                    intent.putExtra("totalPrice",tupel.getValue3().toString());
                                    intent.putExtra("privacyValue",data.getString("privacyValue"));
                                    intent.putExtra("anonymityValue",data.getString("anonymity"));
                                    startActivity(intent);
                                }catch (Exception e){
                                    Log.i("Error_LOG", e.getMessage());
                                }
                            }
                        });
                    }
                    ll.addView(b);
                    //If is Request
                }else{
                    String text = MainActivity.getInfoString(data);
                    b.setText(text);
                    if(MainActivity.isAnonymized(data.getString("anonymity"))){
                        b.setBackgroundResource(R.drawable.sellanonymizedbutton);
                        b.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(getApplicationContext(), DetailView.class);
                                    intent.putExtra("isSellable","false");
                                    intent.putExtra("dataType",data.getString("dataType"));
                                    intent.putExtra("age","");
                                    intent.putExtra("gender","");
                                    intent.putExtra("educationString","");
                                    intent.putExtra("startDataDate",data.getString("startDate"));
                                    intent.putExtra("stopDataDate", data.getString("endDate"));
                                    intent.putExtra("validUntil",data.getString("validUntilDate"));
                                    intent.putExtra("amountOfData",data.getString("amountOfData"));
                                    intent.putExtra("metaDate",data.getString("metaData"));
                                    intent.putExtra("gatekeeperIP",data.getString("gatekeeper"));
                                    intent.putExtra("expectedPrice",data.getString("expectedPrice"));
                                    intent.putExtra("totalPrice",tupel.getValue3().toString());
                                    intent.putExtra("privacyValue",data.getString("privacyValue"));
                                    intent.putExtra("anonymityValue",data.getString("anonymity"));
                                    startActivity(intent);
                                }catch (Exception e){
                                    Log.i("Error_LOG", e.getMessage());
                                }
                            }
                        });
                    }else{
                        b.setBackgroundResource(R.drawable.sellbutton);
                        b.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(getApplicationContext(), DetailView.class);
                                    intent.putExtra("isSellable","false");
                                    intent.putExtra("dataType",data.getString("dataType"));
                                    intent.putExtra("age",data.getString("age"));
                                    intent.putExtra("gender",data.getString("gender"));
                                    intent.putExtra("startDataDate",data.getString("startDate"));
                                    intent.putExtra("stopDataDate", data.getString("endDate"));
                                    intent.putExtra("validUntil",data.getString("validUntilDate"));
                                    intent.putExtra("amountOfData",data.getString("amountOfData"));
                                    intent.putExtra("metaDate",data.getString("metaData"));
                                    intent.putExtra("gatekeeperIP",data.getString("gatekeeper"));
                                    intent.putExtra("expectedPrice",data.getString("expectedPrice"));
                                    intent.putExtra("totalPrice",tupel.getValue3().toString());
                                    intent.putExtra("privacyValue",data.getString("privacyValue"));
                                    intent.putExtra("anonymityValue",data.getString("anonymity"));
                                    startActivity(intent);
                                }catch (Exception e){
                                    Log.i("Error_LOG", e.getMessage());
                                }
                            }
                        });
                    }
                    ll.addView(b);
                }
            }catch (Exception e){
                Log.i("LOG_TAG",e.getMessage());
            }
        }
    }

}
