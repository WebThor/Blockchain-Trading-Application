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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.datasell.GPS_Service_Package.GPSData;
import com.example.datasell.Validator_Package.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.abi.datatypes.Int;
import org.web3j.protocol.Web3j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewRequest extends AppCompatActivity {

    private static final String LOGTAG = "OFFER_LOG";
    private SeekBar seekBar;
    private TextView privacyTextView;
    private EditText expectedPriceField;
    private Spinner genderSpinner;
    private TextView totalDataValueTextfield;
    private int privacyValue = 0;
    private int showProgressValue = 0;
    private double currentPrice;
    //Fields for Input
    private TextView ageField;
    private EditText offerValidUntil;
    private TextView education;
    private TextView metaData;
    private TextView gender;
    private TextView amountOfData;
    private EditText toDate;
    private EditText fromData;
    private Spinner typeOfData;
    private Spinner riskSpinner;
    private TextView gateKeeper;
    private String totalPrice;
    private double amount;
    private Calendar myCalendar;
    private Web3j web3j;
    private double currentETHRate = 120;
    private String uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

        typeOfData = (Spinner) findViewById(R.id.typeOfDataFieldRequest);
        genderSpinner = (Spinner) findViewById(R.id.genderFieldRequest);
        riskSpinner = (Spinner) findViewById(R.id.riskSpinnerRequest);
        ageField = (TextView) findViewById(R.id.ageFieldRequestRequest);
        education = (TextView) findViewById(R.id.educationFieldRequest);
        fromData = (EditText) findViewById(R.id.fromDataRequest);
        toDate = (EditText) findViewById(R.id.toDataRequest);
        amountOfData = (EditText) findViewById(R.id.amountOfDateFieldRequest);
        metaData = (TextView) findViewById(R.id.furtherMetaDataRequest);
        offerValidUntil = (EditText) findViewById(R.id.offerValideUntilRequest);
        gateKeeper = (TextView) findViewById(R.id.gatekeeperFieldRequest);
        seekBar = (SeekBar) findViewById(R.id.seekBarViewRequest);
        privacyTextView = (TextView) findViewById(R.id.currentPrivacyValueFieldRequest);
        expectedPriceField = (EditText) findViewById(R.id.expectedPriceFieldRequest);
        totalDataValueTextfield = (TextView) findViewById(R.id.totalDataValueFieldRequest);


        List<String> list = new ArrayList<String>();
        list.add("Choose Gender");
        list.add("Male");
        list.add("Female");
        list.add("Both");
        initSpinner(genderSpinner,list);
        initSpinner(typeOfData,getListOfferableData());
        initSpinner(riskSpinner,getRiskFunctions());
        myCalendar = Calendar.getInstance();
        toDate.setKeyListener(null);
        fromData.setKeyListener(null);
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
        privacyTextView.setText("Max. Privacy Value: " + showProgressValue );
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                privacyValue = seekBar.getMax() - progressValue;
                showProgressValue = progressValue;
                privacyTextView.setText("Max. Privacy Value: " + showProgressValue );
                updateTotalPrice();


            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                privacyTextView.setText("Max. Privacy Value: " + showProgressValue );
                updateTotalPrice();


            }
        });

        typeOfData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
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
                DatePickerDialog datePicker = new DatePickerDialog(NewRequest.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker =  new DatePickerDialog(NewRequest.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker =  new DatePickerDialog(NewRequest.this, new DatePickerDialog.OnDateSetListener() {
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

    public void onClickNextPlaceRequest(View v){
        try {
            Intent intent = new Intent(getApplicationContext(), PlaceOrder.class);
            intent.putExtra("isOffer","false");
            intent.putExtra("dataType",typeOfData.getSelectedItem().toString());
            intent.putExtra("age",ageField.getText().toString());
            intent.putExtra("gender",genderSpinner.getSelectedItem().toString());
            intent.putExtra("amountOfData",amountOfData.getText().toString());
            intent.putExtra("educationString",education.getText().toString());
            intent.putExtra("startDataDate",fromData.getText().toString());
            intent.putExtra("stopDataDate", toDate.getText().toString());
            intent.putExtra("validUntil",offerValidUntil.getText().toString());
            intent.putExtra("metaDate",metaData.getText().toString());
            intent.putExtra("gatekeeperIP",gateKeeper.getText().toString());
            intent.putExtra("exchangeRate",String.valueOf(currentETHRate));
            intent.putExtra("totalPrice",String.valueOf(amount));
            intent.putExtra("expectedPrice",expectedPriceField.getText().toString());
            intent.putExtra("privacyValue",String.valueOf(seekBar.getMax() - privacyValue));
            intent.putExtra("anonymityValue",riskSpinner.getSelectedItem().toString());
            intent.putExtra("blockchainURI",uri);
            startActivity(intent);
        }catch (Exception e){
            Log.i("Error_LOG", e.getMessage());
        }
    }


    private void initSpinner(Spinner spinner, List<String> list){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


    private void updateLabel(EditText label) {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTotalPrice(){
        if(Validator.isOfferCorrect(fromData.getText().toString(),toDate.getText().toString(),offerValidUntil.getText().toString())
                && typeOfData.getSelectedItemPosition() == 1 && Validator.isInputDecimal(amountOfData.getText().toString())
                && !expectedPriceField.getText().toString().isEmpty()){

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date dateFrom = dateFormat.parse(fromData.getText().toString());
                Date dateTo = dateFormat.parse(toDate.getText().toString());
                currentPrice = Double.parseDouble(expectedPriceField.getText().toString());
                amount = Double.parseDouble(amountOfData.getText().toString());
                amount =  Double.parseDouble(String.format(Locale.US,"%.3g%n",currentPrice * amount));
                Log.i("GPS_LOG",String.valueOf(amountOfData));
                totalDataValueTextfield.setText("Total: (€) " + amount + " for " + amountOfData.getText().toString() + " data sets");
            } catch(Exception e) {
            }
        }
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



}
