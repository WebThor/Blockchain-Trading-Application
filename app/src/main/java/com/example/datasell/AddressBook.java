package com.example.datasell;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class AddressBook extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610345806100206000396000f3fe6080604052600436106100345760003560e01c806338eada1c146100395780634ba79dfe14610061578063a39fac1214610087575b600080fd5b61005f6004803603602081101561004f57600080fd5b50356001600160a01b03166100ec565b005b61005f6004803603602081101561007757600080fd5b50356001600160a01b031661013b565b34801561009357600080fd5b5061009c61020b565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156100d85781810151838201526020016100c0565b505050509050019250505060405180910390f35b600080546001810182559080527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e5630180546001600160a01b0319166001600160a01b0392909216919091179055565b60006101468261026e565b905060001981146102075780805b600054600019018110156101c857600080546001830190811061017357fe5b600091825260208220015481546001600160a01b0390911691908390811061019757fe5b600091825260209091200180546001600160a01b0319166001600160a01b0392909216919091179055600101610154565b506000805460001981019081106101db57fe5b6000918252602082200180546001600160a01b03191690558054906102049060001983016102d2565b50505b5050565b6060600080548060200260200160405190810160405280929190818152602001828054801561026357602002820191906000526020600020905b81546001600160a01b03168152600190910190602001808311610245575b505050505090505b90565b600080805b6000548110156102c557836001600160a01b031660008281548110151561029657fe5b6000918252602090912001546001600160a01b031614156102b9575090506102cd565b60019182019101610273565b506000199150505b919050565b8154818355818111156102f6576000838152602090206102f69181019083016102fb565b505050565b61026b91905b808211156103155760008155600101610301565b509056fea165627a7a72305820a81c468a83e4539b8cdcad5112e8e2fa1d838834dc2374c7130f85856fc9364e0029";

    protected AddressBook(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AddressBook(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> addAddress(String addr, BigInteger weiValue) {
        final Function function = new Function(
                "addAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> removeAddress(String addr, BigInteger weiValue) {
        final Function function = new Function(
                "removeAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<List> getAddresses() {
        final Function function = new Function("getAddresses", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public static RemoteCall<AddressBook> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AddressBook.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AddressBook> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AddressBook.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static AddressBook load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AddressBook(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static AddressBook load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AddressBook(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
