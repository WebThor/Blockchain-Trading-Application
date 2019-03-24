package com.example.datasell;

public class User {

    private String address;
    private int isGPSCollecting;
    private int isAPPCollecting;

    public User(String address, int isGPSCollecting, int isAPPCollecting){
        this.address = address;
        this.isGPSCollecting = isGPSCollecting;
        this.isAPPCollecting = isAPPCollecting;
    }

    public User(){

    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsGPSCollecting() {
        return isGPSCollecting;
    }

    public void setIsGPSCollecting(int isGPSCollecting) {
        this.isGPSCollecting = isGPSCollecting;
    }

    public int getIsAPPCollecting() {
        return isAPPCollecting;
    }

    public void setIsAPPCollecting(int isAPPCollecting) {
        this.isAPPCollecting = isAPPCollecting;
    }
}
