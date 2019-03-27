package com.example.datasell;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class Deal extends Contract {
    private static final String BINARY = "608060405260405161064f38038061064f8339810180604052606081101561002657600080fd5b81516020830180519193928301929164010000000081111561004757600080fd5b8201602081018481111561005a57600080fd5b815164010000000081118282018710171561007457600080fd5b5050602090910151600180546001600160a01b03199081163317909155600080546001600160a01b038881169190931617808255604080517f38eada1c0000000000000000000000000000000000000000000000000000000081523060048201529051959750939550909116926338eada1c92602480820193929182900301818387803b15801561010457600080fd5b505af1158015610118573d6000803e3d6000fd5b5050835161012f925060039150602085019061013b565b50600455506101d69050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061017c57805160ff19168380011785556101a9565b828001600101855582156101a9579182015b828111156101a957825182559160200191906001019061018e565b506101b59291506101b9565b5090565b6101d391905b808211156101b557600081556001016101bf565b90565b61046a806101e56000396000f3fe6080604052600436106100555760003560e01c8063603daf9a1461005a5780636ac06c7d1461008b578063893d20e81461012e578063a39fac1214610143578063b0532349146101a8578063d835293e146101b2575b600080fd5b34801561006657600080fd5b5061006f6101ba565b604080516001600160a01b039092168252519081900360200190f35b34801561009757600080fd5b506100a06101c9565b604080516001600160a01b0385168152908101829052606060208083018281528551928401929092528451608084019186019080838360005b838110156100f15781810151838201526020016100d9565b50505050905090810190601f16801561011e5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561013a57600080fd5b5061006f610281565b34801561014f57600080fd5b50610158610290565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561019457818101518382015260200161017c565b505050509050019250505060405180910390f35b6101b0610367565b005b6101b06103a0565b6002546001600160a01b031690565b6001805460045460038054604080516020600261010098851615989098026000190190931696909604601f810183900483028701830190915280865260009560609587956001600160a01b0390911694939092849183018282801561026f5780601f106102445761010080835404028352916020019161026f565b820191906000526020600020905b81548152906001019060200180831161025257829003601f168201915b50505050509150925092509250909192565b6001546001600160a01b031690565b6000805460408051600160e11b6351cfd60902815290516060936001600160a01b039093169263a39fac129260048082019391829003018186803b1580156102d757600080fd5b505afa1580156102eb573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561031457600080fd5b81019080805164010000000081111561032c57600080fd5b8201602081018481111561033f57600080fd5b815185602082028301116401000000008211171561035c57600080fd5b509094505050505090565b6001546001600160a01b0316331461037e57600080fd5b6002546001600160a01b0316151561039e576001546001600160a01b0316ff5b565b60045434146103ae57600080fd5b600280546001600160a01b031916331790556001546004546040516001600160a01b03929092169181156108fc0291906000818181858888f193505050501580156103fd573d6000803e3d6000fd5b5060408051338152346020820152428183015290517f0b1bfe71e28a567ff82336ff0d85916a7b5ccc831c26106043451f582381ff849181900360600190a156fea165627a7a72305820cdb3948ff8e8f666f1f579084dd2130768c48cc335fdcc8b34ac2e04db7b30db0029";

    protected Deal(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Deal(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<LogWithdrawalEventResponse> getLogWithdrawalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogWithdrawal", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<LogWithdrawalEventResponse> responses = new ArrayList<LogWithdrawalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogWithdrawalEventResponse typedResponse = new LogWithdrawalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogWithdrawalEventResponse> logWithdrawalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogWithdrawal", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogWithdrawalEventResponse>() {
            @Override
            public LogWithdrawalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                LogWithdrawalEventResponse typedResponse = new LogWithdrawalEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<SafepaySentEventResponse> getSafepaySentEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SafepaySent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<SafepaySentEventResponse> responses = new ArrayList<SafepaySentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SafepaySentEventResponse typedResponse = new SafepaySentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.now = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SafepaySentEventResponse> safepaySentEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SafepaySent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SafepaySentEventResponse>() {
            @Override
            public SafepaySentEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                SafepaySentEventResponse typedResponse = new SafepaySentEventResponse();
                typedResponse.log = log;
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.now = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<String> getBuyer() {
        final Function function = new Function("getBuyer", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> returnContractDetails() {
        final Function function = new Function("returnContractDetails", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<String> getOwner() {
        final Function function = new Function("getOwner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> _destroyContract(BigInteger weiValue) {
        final Function function = new Function(
                "_destroyContract", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> sendSafepay(BigInteger weiValue) {
        final Function function = new Function(
                "sendSafepay", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static RemoteCall<Deal> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _addressBook, String _data, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_addressBook), 
                new org.web3j.abi.datatypes.Utf8String(_data), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(Deal.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<Deal> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _addressBook, String _data, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_addressBook), 
                new org.web3j.abi.datatypes.Utf8String(_data), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)));
        return deployRemoteCall(Deal.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Deal load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Deal(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Deal load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Deal(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class LogWithdrawalEventResponse {
        public Log log;

        public String sender;

        public BigInteger amount;
    }

    public static class SafepaySentEventResponse {
        public Log log;

        public String buyer;

        public BigInteger value;

        public BigInteger now;
    }
}
