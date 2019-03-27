package com.example.datasell;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    private final String MAIN_LOG = "MAIN_LOG";
    private SQLiteDatabaseHandlerGPS db;
    private Web3j web3j;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        uri = (String) intent.getSerializableExtra("BlockchainURI");

        Log.i("connectionLog_URI",uri);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initBlockchainConnection(uri);
         db = new SQLiteDatabaseHandlerGPS(this);
         db.addUser(BlockchainManager.getADDRESSBOOK());
         db.close();
    }

    public void refreshButton(View v){
        db = new SQLiteDatabaseHandlerGPS(this);
        Log.i(MAIN_LOG,String.valueOf(db.hasGPSData()));
       db.close();
    }

    private void initBlockchainConnection(String uri){
        web3j = BlockchainManager.connectToEthereumTestnet(uri);
        if(web3j != null){
            setOnline(true);
            getOffers(findViewById(R.id.refreshButton));
        }else {
            setOnline(false);
        }

    }

    public void getOffers(View view){
        LinearLayout ll = (LinearLayout) findViewById(R.id.offerLinearLayout);
        List<String> addresses = BlockchainManager.getAddressesFromAddressbook(BlockchainManager.loadDefaultAddressbook(web3j,BlockchainManager.getCredentialsFromPrivateKey()));
        Log.i("blockchain_call",addresses.toString());
        for (String s : addresses){
            Button b = new Button(this);
            b.setText(s);
            ll.addView(b);
       }
    }


    private void setOnline(boolean isOnline){
        if(isOnline){
            NavigationView naviView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = naviView.getHeaderView(0);
            ImageView imageView = (ImageView) headerView.findViewById(R.id.navImageView);
            imageView.setImageResource(R.mipmap.ic_online_round);
        }
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_offer) {
            Intent intent = new Intent(this, NewOffer.class);
            intent.putExtra("blockchainURI",uri);
            startActivity(intent);
        } else if (id == R.id.nav_request) {
            startActivity(new Intent(this,ConnectionActivity.class));
        } else if (id == R.id.nav_myOffers) {
            startActivity(new Intent(this, MyOffers.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, History.class));
        } else if (id == R.id.nav_data) {
            startActivity(new Intent(this, DataStore.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
