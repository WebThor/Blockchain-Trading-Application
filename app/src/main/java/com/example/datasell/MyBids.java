package com.example.datasell;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple2;

import java.util.List;

public class MyBids extends Activity {

    private String uri;
    private SQLiteDatabaseHandlerGPS db;
    private LinearLayout ll;
//TODO GET BUYER and check if I am the buyer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);
        uri = getIntent().getStringExtra("blockchainURI");
        setUpView();
    }


    private void setUpView(){
        db = new SQLiteDatabaseHandlerGPS(this);
        ll = (LinearLayout) findViewById(R.id.bidsLindearLayout);
        ll.removeAllViews();
        List<Tuple2<String,String>> list = db.getAllBids();
        Web3j web3j = BlockchainManager.connectToEthereumTestnet(uri);

        for(Tuple2<String,String> t : list){
            Button b = new Button(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Deal deal = BlockchainManager.loadDeal(t.getValue1(),web3j,BlockchainManager.getCredentialsFromPrivateKey(sharedPreferences.getString("privatekey", "")));
            int state = getStateOfTheOffer(deal);
            b.setBackgroundResource(state);
            if(state == R.drawable.sellbutton){
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {

                            new AlertDialog.Builder(MyBids.this)
                                    .setTitle("Buy entry")
                                    .setMessage("Are you sure you want to buy this offer?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            BlockchainManager.deleteBid(deal,BlockchainManager.getMyAddress());
                                            db.removeBid(t.getValue1());
                                            db.addPurchase(t.getValue1());
                                            setUpView();
                                            Toast.makeText(getApplicationContext(),"You bought the data",Toast.LENGTH_LONG).show();

                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }catch (Exception e){
                            Log.i("Error_LOG", e.getMessage());
                        }
                    }
                });
            }else if(state == R.drawable.buybutton){
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Toast.makeText(getApplicationContext(),"Someone Else Got The Data",Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Log.i("Error_LOG", e.getMessage());
                        }
                    }
                });

            }else{
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Toast.makeText(getApplicationContext(),"The Seller has not decided, yet",Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Log.i("Error_LOG", e.getMessage());
                        }
                    }
                });

            }
            b.setText(t.getValue1());
            ll.addView(b);
        }
    }
    //0 = not set, 1 = Buyer, 2 = other buyer
    private int getStateOfTheOffer(Deal deal){
        try {
            String value = deal.getAllowedBuyer().sendAsync().get();
            Log.i("LOG_TAG",value);
            if(value.equals("0x0000000000000000000000000000000000000000")){
                return R.drawable.notyetsetbutton;
            }else if(value.equals(BlockchainManager.getMyAddress())){
                return R.drawable.sellbutton;
            }else{
                return R.drawable.buybutton;
            }

        }catch (Exception e){

        }
        return R.drawable.notyetsetbutton;
    }
}
