package com.example.datasell;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BlockchainManager {
    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

    public static String getADDRESSBOOK() {
        return ADDRESSBOOK;
    }

    private final static String ADDRESSBOOK = "0xde0ed64b8922be86361e5e2de9fa7ec265f196c8";
    private  Web3j web3;
    private Credentials creds;
    private Context context;

    protected  BlockchainManager(Context context){
        this.context = context;
    }


    private Credentials getCredentialsFromPrivateKey(){
        return Credentials.create("02a792551c350f083805f687f8a21776bbfd4d2b9c73ae3df73a38db41431511");
    }

    protected boolean connectToEthereumTestnet(String url){
        web3 = Web3jFactory.build(new HttpService(url));  // defaults to http://localhost:8545/
        creds = getCredentialsFromPrivateKey();
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if(!clientVersion.hasError()){
                Toast.makeText(context,clientVersion.getWeb3ClientVersion(),Toast.LENGTH_LONG);
                return true;
            }else{
                return false;
            }
        }
        catch (Exception e) {
            Log.i("ErrorLog",e.getMessage());
            return false;
        }

    }

    protected String deployAddressContract(){
        try {
            return AddressBook.deploy(web3,creds, GAS_PRICE,GAS_LIMIT).sendAsync().get().getContractAddress();
        }catch (InterruptedException e){
            Log.i("ErrorLog",e.getMessage());
        }catch(ExecutionException e){
            Log.i("ErrorLog",e.getMessage());
        }
        return "";
    }

    protected AddressBook loadDefaultAddressbook(){
        return AddressBook.load(ADDRESSBOOK,web3,creds,GAS_PRICE,GAS_LIMIT);
    }


    //Contract deployed on: "0x064c182B2b05529BBb10b28fb4D207E286D7F58f"
    //    addressBook.addAddress("0xC9b08ea6020F362fdaE6A586bdb5FcC9b1Fa898c","Tom").sendAsync();
    protected AddressBook loadAddressbookContract(String address){
        return AddressBook.load(address,web3,creds,GAS_PRICE,GAS_LIMIT);
    }

    protected void addAddressToAddressbook(AddressBook addressBook, String address){
      addressBook.addAddress(address,GAS_LIMIT);
    }

    protected List<String> getAddressesFromAddressbook(AddressBook addressBook){
        List<String> list = new ArrayList<>();
        try {
            list = addressBook.getAddresses().sendAsync().get();
        }catch (Exception e){
            Log.i("ErrorLog",e.getMessage());
        }
        return list;
    }

}
