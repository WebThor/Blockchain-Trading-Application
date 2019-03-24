package com.example.datasell.GPS_Service_Package;

public class GPSData {

    private String time;
    private String longitude;
    private String latitude;


    public GPSData(String time,String longitude,String latitude){
        this.time=time;
        this.latitude = longitude;
        this.latitude = latitude;
    }

    public GPSData(){

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }



}
