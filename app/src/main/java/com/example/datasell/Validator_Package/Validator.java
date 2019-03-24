package com.example.datasell.Validator_Package;

import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Validator {


        public static boolean isThisDateValid(String dateToValidate, String dateFromat){

            if(dateToValidate == null){
                return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
            sdf.setLenient(false);

            try {

                //if not valid, it will throw ParseException
                Date date = sdf.parse(dateToValidate);


            } catch (ParseException e) {

                e.printStackTrace();
                return false;
            }

            return true;

    }


    private static boolean isValideBirthyear(String birthyear){
        Log.i("MAIN_LOG",birthyear);
        if(birthyear == ""){
            return false;
        }
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int birthyearInt = Integer.parseInt(birthyear);
        int age = year -birthyearInt;

        if(age <= 120 && age >= 0 ){
            return true;
        }else{
            return false;
        }


    }

    private static boolean isOldEnough(String birthyear) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int birthyearInt = Integer.parseInt(birthyear);

        if(isValideBirthyear(birthyear) && year - birthyearInt >= 18){
            return true;
        }else{
            return false;
        }
    }

    public static boolean verifyAgeField(TextView ageField){
        String birthyear = ageField.getText().toString();
        if(ageField.getText().toString().equals("") && ageField.getText().length() <= 0){
            ageField.setError("Please enter your birth year");
            return false;
        }else if(!Validator.isValideBirthyear(birthyear)){
                ageField.setError("Please enter a valid year");
                return false;
        }else if(!Validator.isOldEnough(birthyear)){
               ageField.setError("You have to be at least 18");
               return false;
        }else {
            return true;
        }
    }

    public static boolean verifyGender(Spinner gender){
        if(gender.getSelectedItem().toString() == "gender"){
            return false;
        }else {
            return true;
        }
    }

    public static boolean isDateOneBigger(String dateOne, String dateTwo){
            if(isThisDateValid(dateOne,"dd.mm.yyyy") && isThisDateValid(dateTwo,"dd.mm.yyyy")){
                SimpleDateFormat formatter1=new SimpleDateFormat("dd.MM.yyyy");
                try {
                    Date date1=formatter1.parse(dateOne);
                    long dateTime1 = date1.getTime();
                    Date date2 = formatter1.parse(dateTwo);
                    long dateTime2 = date2.getTime();
                    if(dateTime1 -dateTime2 >= 0){
                        return true;
                    }else {
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }else{
                return false;
            }
    }

    public static boolean checkIfIsEmpty(TextView input){
        if(input.getText().toString().equals("") && input.getText().length() <= 0){
            return true;
        }else {
            return false;
        }
    }

    public static boolean validateIfIP(TextView ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if(checkIfIsEmpty(ip)){
            ip.setError("Please enter a Gatekeeper IP");
            return false;
        }else if(ip.getText().toString().matches(PATTERN)){
            return true;
        }else{
            ip.setError("No valid IP");
            return false;
        }
    }

    public static boolean chooseRightData(Spinner typeOfData){
            if(typeOfData.getSelectedItem().toString() != "Choose Data"){
                return true;
            }else {
                return false;
            }
    }

    public static boolean isOfferCorrect(String fromDate, String toDate, String validDate){
        if(Validator.isThisDateValid(fromDate,"dd.mm.yyyy")
        && Validator.isThisDateValid(toDate,"dd.mm.yyyy")
        && Validator.isThisDateValid(validDate,"dd.mm.yyyy")
        && isDateOneBigger(validDate,toDate)){
            return true;
        }else{
            return false;
        }
    }
}