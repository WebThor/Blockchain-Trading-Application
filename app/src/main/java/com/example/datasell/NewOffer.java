package com.example.datasell;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datasell.GPS_Service_Package.GPSData;
import com.example.datasell.Validator_Package.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;


public class NewOffer extends AppCompatActivity {

    private static final String LOGTAG = "OFFER_LOG";
    private SeekBar seekBar;
    private TextView privacyTextView;
    private TextView expectedPriceField;
    private Spinner genderSpinner;
    private TextView totalDataValueTextfield;
    private int privacyValue = 0;
    private int showProgressValue = 0;
    private double currentPrice;
    //Fields for Input
    private TextView birthDayField;
    private EditText offerValidUntil;
    private TextView education;
    private TextView metaData;
    private TextView gender;
    private EditText fromData;
    private EditText toDate;
    private Spinner typeOfData;
    private Spinner riskSpinner;
    private TextView gateKeeper;
    private SQLiteDatabaseHandlerGPS db;
    private String totalPrice;

    private Calendar myCalendar;
    private Web3j web3j;
    private double currentETHRate = 120;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        seekBar = (SeekBar) findViewById(R.id.seekBarView);
        privacyTextView = (TextView) findViewById(R.id.currentPrivacyValueField);
        expectedPriceField = (TextView) findViewById(R.id.expectedPriceField);
        genderSpinner = (Spinner) findViewById(R.id.genderField);
        typeOfData = (Spinner) findViewById(R.id.typeOfDataField);
        riskSpinner = (Spinner) findViewById(R.id.riskSpinner);
        birthDayField = (TextView) findViewById(R.id.birthField);
        totalDataValueTextfield = (TextView) findViewById(R.id.totalDataValueField);
        List<String> list = new ArrayList<String>();
        list.add("Choose Gender");
        list.add("Male");
        list.add("Female");
       initSpinner(genderSpinner,list);
       initSpinner(typeOfData,getListOfferableData());
       initSpinner(riskSpinner,getRiskFunctions());
       offerValidUntil = (EditText) findViewById(R.id.offerValideUntil);
        education = (TextView) findViewById(R.id.educationField);
        metaData = (TextView) findViewById(R.id.furtherMetaData);
        fromData = (EditText) findViewById(R.id.fromDateField);
        toDate = (EditText) findViewById(R.id.toDateField);
        gateKeeper = (TextView) findViewById(R.id.gatekeeperField);
        db = new SQLiteDatabaseHandlerGPS(this);
        myCalendar = Calendar.getInstance();
        fromData.setKeyListener(null);
        toDate.setKeyListener(null);
        offerValidUntil.setKeyListener(null);
        uri = getIntent().getStringExtra("blockchainURI");


        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=EUR", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                //You can test it by printing response.substring(0,500) to the screen.
                try {
                    JSONObject obj = new JSONObject(response);
                    currentETHRate = obj.getDouble("EUR");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JSON",error.getMessage());
            }
        });
        ExampleRequestQueue.add(ExampleStringRequest);

        privacyTextView.setText("Privacy Value: " + showProgressValue );
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                privacyValue = seekBar.getMax() - progressValue;
                showProgressValue = progressValue;
                privacyTextView.setText("Privacy Value: " + showProgressValue );
                currentPrice = Double.parseDouble(String.format(Locale.US,"%.3g%n",getPrice()));
                String output = "Price (€): " + currentPrice;
                expectedPriceField.setText(output);
                updateTotalPrice();


            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                privacyTextView.setText("Privacy Value: " + showProgressValue );
                currentPrice = Double.parseDouble(String.format(Locale.US,"%.3g%n",getPrice()));
                String output = "Price (€): " + currentPrice;
                expectedPriceField.setText(output);
                updateTotalPrice();


            }
        });

        typeOfData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentPrice = Double.parseDouble(String.format(Locale.US,"%.3g%n",getPrice()));
                String output = "Price (€): " + currentPrice;
                expectedPriceField.setText(output);
                updateTotalPrice();

              }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        fromData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePicker = new DatePickerDialog(NewOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(fromData);
                        updateTotalPrice();
                    }
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePicker.show();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePicker =  new DatePickerDialog(NewOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(toDate);
                        updateTotalPrice();
                    }
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePicker.show();
            }
        });


        offerValidUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker =  new DatePickerDialog(NewOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(offerValidUntil);
                        updateTotalPrice();
                    }
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                datePicker.show();
            }
        });


    }

    private void updateLabel(EditText label) {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTotalPrice(){
        if(Validator.isOfferCorrect(fromData.getText().toString(),toDate.getText().toString(),offerValidUntil.getText().toString())
        && typeOfData.getSelectedItemPosition() == 1){

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date dateFrom = dateFormat.parse(fromData.getText().toString());
                Date dateTo = dateFormat.parse(toDate.getText().toString());
                List<GPSData> list =db.getAllGPSPositionsInDateRange(String.valueOf(dateFrom.getTime()),String.valueOf(dateTo.getTime()));
                double amountOfData = db.getAllGPSPositionsInDateRange(String.valueOf(dateFrom.getTime()),String.valueOf(dateTo.getTime())).size();
                amountOfData =  Double.parseDouble(String.format(Locale.US,"%.3g%n",currentPrice * amountOfData));
                Log.i("GPS_LOG",String.valueOf(amountOfData));
                totalDataValueTextfield.setText("Total: (€) " + amountOfData + " for " + list.size() + " data sets");
                totalPrice = String.valueOf(amountOfData);
            } catch(Exception e) {
            }


        }
    }

    private double getPrice(){
        double price = getEstimateDataValue();
        if(riskSpinner.getSelectedItemPosition() == 0){
            price = price * getConservativeValue((double) privacyValue)*10;
        }else{
            price = price * getLiberalValue((double) privacyValue)*10;
        }
        return price;
    }




    public void onClickNext(View v){
        web3j = BlockchainManager.connectToEthereumTestnet(uri);
        if(Validator.verifyAgeField(birthDayField)
        && Validator.verifyGender(genderSpinner)
        && Validator.isThisDateValid(fromData.getText().toString(),"dd.mm.yyyy")
        && Validator.isThisDateValid(toDate.getText().toString(),"dd.mm.yyyy")
        && Validator.isThisDateValid(offerValidUntil.getText().toString(),"dd.mm.yyyy")
        && Validator.isDateOneBigger(offerValidUntil.getText().toString(),toDate.getText().toString())
        && Validator.validateIfIP(gateKeeper)
        && Validator.chooseRightData(typeOfData)){
            if(web3j != null){

                Intent intent = new Intent(this, PlaceOrder.class);
                intent.putExtra("isOffer","true");
                intent.putExtra("dataType",typeOfData.getSelectedItem().toString());
                intent.putExtra("birthyear",birthDayField.getText().toString());
                intent.putExtra("gender",genderSpinner.getSelectedItem().toString());
                intent.putExtra("educationString",education.getText().toString());
                intent.putExtra("startDataDate",fromData.getText().toString());
                intent.putExtra("stopDataDate", toDate.getText().toString());
                intent.putExtra("validUntil",offerValidUntil.getText().toString());
                intent.putExtra("metaDate",metaData.getText().toString());
                intent.putExtra("gatekeeperIP",gateKeeper.getText().toString());
                intent.putExtra("totalPrice",totalPrice);
                double est = currentETHRate * 0.003;
                intent.putExtra("estimatedTransactionCosts", String.valueOf(est));
                intent.putExtra("exchangeRate",String.valueOf(currentETHRate));
                intent.putExtra("blockchainURI",uri);
                intent.putExtra("privacyValue",String.valueOf(seekBar.getMax()-privacyValue));
                intent.putExtra("anonymityValue",riskSpinner.getSelectedItem().toString());
                startActivity(intent);
                setContentView(R.layout.activity_place_order);
            }else{
                Toast.makeText(this,"You are not connected to the Blockchain",Toast.LENGTH_SHORT);
            }


        }
    }

    private double getEstimateDataValue(){
        return 0.15;
    }
    private double getLiberalValue(double price){
        return (((Math.log(30) / Math.log(2) ) * Math.log(9000 * price + 1)) / 130);
    }
    private double getConservativeValue(double price){
        return ((8*price) / Math.sqrt(1100+500*price*price));
    }


    private List<String> getListOfferableData(){
        List<String> dataType = new ArrayList<String>();
        SQLiteDatabaseHandlerGPS db = new SQLiteDatabaseHandlerGPS(this);
        dataType.add("Choose Data");
        if(db.hasGPSData()){
            dataType.add("GPS Data");
        }
        return dataType;
    }

    private List<String> getRiskFunctions(){
        List<String> dataType = new ArrayList<String>();
       dataType.add("Low-Risk, Low-Return");
       dataType.add("High-Risk, High-Return");
       return dataType;
    }

    private void initSpinner(Spinner spinner, List<String> list){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

}
