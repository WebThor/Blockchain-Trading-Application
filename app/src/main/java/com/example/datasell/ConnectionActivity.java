package com.example.datasell;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class ConnectionActivity extends AppCompatActivity  {


    // UI references.
    private TextView ipAddress;
    private TextView port;
    private View mProgressView;
    private View mLoginFormView;
    private Spinner dropdownAddressbook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        // Set up the connection form.
        ipAddress = (TextView) findViewById(R.id.ipAddress);
        port = (TextView) findViewById(R.id.port);
        dropdownAddressbook = (Spinner) findViewById(R.id.deafultAddressbookDropdown);

        List<String> list = new ArrayList<String>();
        list.add("Choose default Addressbook");
        list.add("0x0000");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownAddressbook.setAdapter(dataAdapter);


        Button connectionButton = (Button) findViewById(R.id.connectButton);
        connectionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }



    private void attemptLogin() {
               // Reset errors.
        ipAddress.setError(null);
        port.setError(null);

        // Store values at the time of the login attempt.
        String ip = ipAddress.getText().toString();
        String portValue = port.getText().toString();

        Log.i("connectionLog_ip",ip);
        Log.i("connectionLog_port",portValue);
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
            intent.putExtra("BlockchainURI", uri);
            startActivity(intent);

        }



    }


}

