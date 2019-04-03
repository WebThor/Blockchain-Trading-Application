package com.example.datasell;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;



/**
 * A login screen that offers login via email/password.
 */
public class ConnectionActivity extends AppCompatActivity  {


    // UI references.
    private TextView ipAddress;
    private TextView port;
    private MaterialBetterSpinner dropdownAddressbook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        // Set up the connection form.
        ipAddress = (TextView) findViewById(R.id.ipAddress);
        port = (TextView) findViewById(R.id.port);
        dropdownAddressbook = (MaterialBetterSpinner) findViewById(R.id.deafultAddressbookDropdown);

        List<String> list = new ArrayList<String>();
        list.add("0xdF83faa4c680410964FFB03272a5A8D6f7F8b8BE");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dropdownAddressbook.setAdapter(dataAdapter);


        Button connectionButton = (Button) findViewById(R.id.connectButton);
        connectionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }



    private void attemptLogin() {
               // Reset errors.
        ipAddress.setError(null);
        port.setError(null);

        // Store values at the time of the login attempt.
        String ip = ipAddress.getText().toString();
        String portValue = port.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid port, if the user entered one.
        if (portValue == "") {
            port.setError("Port to short");
            focusView = port;
            cancel = true;
        }

        // Check for a valid ip, if the user entered one.
        if (ip == "") {
            ipAddress.setError("Port to short");
            focusView = ipAddress;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }else{
            String uri = ip + ":" + portValue;
            Log.i("connectionLog_BM",uri);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("blockchainURI", uri);
            startActivity(intent);

        }



    }


}

