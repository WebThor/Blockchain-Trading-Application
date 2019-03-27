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
    protected final static String TEST_ADDRESS = "0x04F50Bfa2716CA33bF683f3b8be186C9Da2e35B9";
    private final static String ADDRESSBOOK = "0xde0ed64b8922be86361e5e2de9fa7ec265f196c8";

    public static String getADDRESSBOOK() {
        return ADDRESSBOOK;
    }




    protected static  Credentials getCredentialsFromPrivateKey(){
        return Credentials.create("02a792551c350f083805f687f8a21776bbfd4d2b9c73ae3df73a38db41431511");
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



    protected static String deployDeal(Web3j web3,Credentials creds, String addressBook,Boolean _isOffer,
                                       String _metaData, String _ageGroup,String  _gender,String _education,
                                       BigInteger _duration, BigInteger _price, String  _typeOfData, String  _gateKeeper){
        try {
            BigInteger i =  BigInteger.valueOf(0L);
            return Deal.deploy(web3,creds, GAS_PRICE,GAS_LIMIT,i,addressBook,_isOffer,_metaData,_ageGroup,_gender,_education,_duration,_price,_typeOfData,_gateKeeper).sendAsync().get().getContractAddress();
        }catch (InterruptedException e){
            Log.i("ErrorLog",e.getMessage());
        }catch(ExecutionException e){
            Log.i("ErrorLog",e.getMessage());
        }
        return "";
    }

}
