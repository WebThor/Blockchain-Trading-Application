package com.example.datasell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONObject;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple2;

import java.util.List;

public class OfferDetails extends AppCompatActivity {

    private String uri;
    private String address;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        ll = (LinearLayout) findViewById(R.id.offersLinearLayout);
        address = getIntent().getStringExtra("contractAddress");
        uri = getIntent().getStringExtra("blockchainURI");
        Web3j web3j = BlockchainManager.connectToEthereumTestnet(uri);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Deal deal = BlockchainManager.loadDeal(address,web3j,BlockchainManager.getCredentialsFromPrivateKey(sharedPreferences.getString("privatekey", "")));
        List<Tuple2<String,String>> tupel = BlockchainManager.getBids(deal);


        for(Tuple2<String,String> t : tupel){
            try {
                JSONObject data = new JSONObject(t.getValue1());
                Button b = new Button(this);
                b.setText("Buyer: " + data.getString("name"));
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(getApplicationContext(), OfferDetailView.class);
                            intent.putExtra("data",t.getValue1());
                            intent.putExtra("blockchainURI",uri);
                            intent.putExtra("buyerAddress",t.getValue2());
                            intent.putExtra("contractAddress",address);
                            startActivity(intent);
                        }catch (Exception e){
                            Log.i("Error_LOG", e.getMessage());
                        }
                    }
                });
                ll.addView(b);
            }catch (Exception e){

            }

        }
    }
}
