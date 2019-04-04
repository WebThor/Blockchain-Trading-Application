package com.example.datasell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class MyPurchases extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        LinearLayout ll = (LinearLayout) findViewById(R.id.purchasesLinearLayout);
        ll.removeAllViews();
        SQLiteDatabaseHandlerGPS db = new SQLiteDatabaseHandlerGPS(this);

        List<String> purchases = db.getAllPurchases();

        for(String s : purchases){
            Button b = new Button(this);
            b.setText(s);
            ll.addView(b);
        }
    }
}
