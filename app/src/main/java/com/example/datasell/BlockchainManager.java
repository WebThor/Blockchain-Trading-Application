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

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public  class BlockchainManager  {
    protected final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    protected final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private final static String ADDRESSBOOK = "0x4e9c9cfe1b3982714de6bb34fdd0353b99921aa6";

    public static String getADDRESSBOOK() {
        return ADDRESSBOOK;
    }




    protected static  Credentials getCredentialsFromPrivateKey(){
        return Credentials.create("ea0b514ab66b89ba6877ea27f3e56d9d33f4b4951cb4930f07edb13933f569d0");
    }

    protected static Credentials getBuyerCredentials(){
        return Credentials.create("6c540fdecca7b39c74b2aab1b5c8fd0e476b6227c60bd4572d21e0f930809d4d");
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

    protected static void makeBid(Deal deal, String data){
        try {
            deal.addBid(data,BigInteger.ZERO).sendAsync().get();
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
    }

    protected static Deal loadDeal(String address,Web3j web3,Credentials creds){
        return Deal.load(address,web3,creds,GAS_PRICE,GAS_LIMIT);
    }

}
