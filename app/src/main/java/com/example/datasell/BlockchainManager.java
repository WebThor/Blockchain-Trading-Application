package com.example.datasell;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public  class BlockchainManager  {
    protected final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    protected final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

    private final static String ADDRESSBOOK = "0x97ff189359c24ebb0ceeaf6a44534c6c6fdbd4a9";


    public static String getADDRESSBOOK() {
        return ADDRESSBOOK;
    }




    protected static  Credentials getCredentialsFromPrivateKey(String s){
        return Credentials.create(s);
    }

    protected static Credentials getBuyerCredentials(){
        //return Credentials.create("d32083a9caf922a7b8e8843b4cb1a23729d7fbea43dd9597c7766b17f6d45ef1");
        return Credentials.create("ffdf94bf708583ec9db28e51b176972d8fdc1c6661a3e0cb6e1f5917ccc20b86");
    }

    protected static String getMyAddress(){
        return "0xEbA38dE3F7E3E9712a6eAAF75D41d5A48E51B45C";
    }

    protected static Web3j connectToEthereumTestnet(String url){
        Web3j web3 = Web3jFactory.build(new HttpService(url));  // defaults to http://localhost:8545/
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if(!clientVersion.hasError()){
                return web3;
            }else{
                return null;
            }
        }
        catch (Exception e) {
            Log.i("ErrorLog",e.getMessage());
            return null;
        }

    }

    protected static String deployAddressContract(Web3j web3,Credentials creds){
        try {
            return AddressBook.deploy(web3,creds, GAS_PRICE,GAS_LIMIT).sendAsync().get().getContractAddress();
        }catch (InterruptedException e){
            Log.i("ErrorLog",e.getMessage());
        }catch(ExecutionException e){
            Log.i("ErrorLog",e.getMessage());
        }
        return "";
    }

    protected static AddressBook loadDefaultAddressbook(Web3j web3,Credentials creds){
        return AddressBook.load(ADDRESSBOOK,web3,creds,GAS_PRICE,GAS_LIMIT);
    }


    //Contract deployed on: "0x064c182B2b05529BBb10b28fb4D207E286D7F58f"
    //    addressBook.addAddress("0xC9b08ea6020F362fdaE6A586bdb5FcC9b1Fa898c","Tom").sendAsync();
    protected static AddressBook loadAddressbookContract(String address,Web3j web3,Credentials creds){
        return AddressBook.load(address,web3,creds,GAS_PRICE,GAS_LIMIT);
    }

    protected static void addAddressToAddressbook(AddressBook addressBook, String address){
      addressBook.addAddress(address,GAS_LIMIT);
    }

    protected static List<String> getAddressesFromAddressbook(AddressBook addressBook){
        List<String> list = new ArrayList<>();
        try {
            list = addressBook.getAddresses().sendAsync().get();
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
        return list;
    }


    protected static boolean isEmptyBuyer(String address){
        if(address.equals("0x0000000000000000000000000000000000000000")){
            return true;
        }else{
            return false;
        }
    }

    protected static String getAllowedBuyer(Deal deal){
        String buyer = "0x0000000000000000000000000000000000000000";
        try {
            buyer = deal.getAllowedBuyer().sendAsync().get();
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
        return buyer;
    }


    protected static String getBuyerOfContract(Deal deal){
        String buyer = "0x0000000000000000000000000000000000000000";
        try {
            buyer = deal.getBuyer().sendAsync().get();
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
        return buyer;
    }

    protected static boolean destroyContract(Deal deal){
        try {
            deal._destroyContract(BigInteger.ZERO).sendAsync().get();
            return true;
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
        return false;
    }

    protected static boolean buyOffer(Deal deal, BigInteger value){
        try {
            BigInteger price = deal.returnContractDetails().sendAsync().get().getValue3();
            if(Double.parseDouble(String.valueOf(value)) >= Double.parseDouble(String.valueOf(price))){
                deal.sendSafepay(value).sendAsync().get();
                return true;
            }
            return false;
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
        return false;
    }

    protected static boolean deleteBid(Deal deal, String bidder){

        try{
            deal.removeBid(bidder, BigInteger.ZERO).sendAsync().get();
            return true;
        }catch(Exception e){

        }
        return false;
    }

    protected static String deployDeal(Web3j web3,Credentials creds, String addressBook, String  data, BigInteger price){
        try {
            BigInteger initialWei = BigInteger.valueOf(0L);
            return Deal.deploy(web3,creds, GAS_PRICE,GAS_LIMIT,initialWei,addressBook,data,price).sendAsync().get().getContractAddress();
        }catch (InterruptedException e){
            Log.i("ErrorLog",e.getMessage());
        }catch(ExecutionException e){
            Log.i("ErrorLog",e.getMessage());
        }
        return "";
    }

    protected static int getBidCount(Deal deal) {
        int i = 0;

        try {
            i = deal.getBidCount().sendAsync().get().intValue();
        } catch (Exception e) {
            Log.i("ErrorLog", e.getMessage());
        }
        return i;
    }

    protected static List<Tuple2<String,String>> getBids(Deal deal){
        int i = getBidCount(deal);
        List<Tuple2<String,String>> values = new LinkedList<>();
        try {
            for (int a = 0; a < i; a++){
                values.add(deal.getBidAtPosition(BigInteger.valueOf((long)a)).sendAsync().get());
            }
        }catch (Exception e){
            Log.i("ErrorLog", e.getMessage());
        }
        return values;
    }

    protected static void makeBid(Deal deal, String data){
        try {
            deal.addBid(data,BigInteger.ZERO).sendAsync().get();
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
    }

    protected static void setAllowedBidder(String address, Deal deal){
        try {
            deal.setAllowerdBuyer(address, BigInteger.ZERO).sendAsync().get();
        }catch (Exception e){

        }
    }

    protected static Deal loadDeal(String address,Web3j web3,Credentials creds){
        return Deal.load(address,web3,creds,GAS_PRICE,GAS_LIMIT);
    }

}
