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
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
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
    private static final String BINARY = "60806040526000600555604051610aeb380380610aeb8339810180604052606081101561002b57600080fd5b81516020830180519193928301929164010000000081111561004c57600080fd5b8201602081018481111561005f57600080fd5b815164010000000081118282018710171561007957600080fd5b5050602090910151600180546001600160a01b03199081163317909155600080546001600160a01b038881169190931617808255604080517f38eada1c0000000000000000000000000000000000000000000000000000000081523060048201529051959750939550909116926338eada1c92602480820193929182900301818387803b15801561010957600080fd5b505af115801561011d573d6000803e3d6000fd5b505083516101349250600391506020850190610140565b50600455506101db9050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061018157805160ff19168380011785556101ae565b828001600101855582156101ae579182015b828111156101ae578251825591602001919060010190610193565b506101ba9291506101be565b5090565b6101d891905b808211156101ba57600081556001016101c4565b90565b610901806101ea6000396000f3fe60806040526004361061009c5760003560e01c80636ac06c7d116100645780636ac06c7d146101dc578063893d20e81461027f578063a39fac1214610294578063b0532349146102f9578063c0f0114114610315578063d835293e146103d05761009c565b806304892b1d146100a157806328f6a48a146100c95780632c780149146100f0578063603daf9a1461012157806363aeecc114610136575b600080fd5b6100c7600480360360208110156100b757600080fd5b50356001600160a01b03166103d8565b005b3480156100d557600080fd5b506100de610411565b60408051918252519081900360200190f35b3480156100fc57600080fd5b50610105610433565b604080516001600160a01b039092168252519081900360200190f35b34801561012d57600080fd5b50610105610474565b6100c76004803603602081101561014c57600080fd5b81019060208101813564010000000081111561016757600080fd5b82018360208201111561017957600080fd5b8035906020019184600183028401116401000000008311171561019b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610483945050505050565b3480156101e857600080fd5b506101f16104d2565b604080516001600160a01b0385168152908101829052606060208083018281528551928401929092528451608084019186019080838360005b8381101561024257818101518382015260200161022a565b50505050905090810190601f16801561026f5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561028b57600080fd5b5061010561058a565b3480156102a057600080fd5b506102a9610599565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156102e55781810151838201526020016102cd565b505050509050019250505060405180910390f35b610301610670565b604080519115158252519081900360200190f35b34801561032157600080fd5b5061033f6004803603602081101561033857600080fd5b50356106b2565b6040518080602001836001600160a01b03166001600160a01b03168152602001828103825284818151815260200191508051906020019080838360005b8381101561039457818101518382015260200161037c565b50505050905090810190601f1680156103c15780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b6100c7610787565b6001546001600160a01b031633146103ef57600080fd5b600680546001600160a01b0319166001600160a01b0392909216919091179055565b6001546000906001600160a01b0316331461042b57600080fd5b506005545b90565b6001546000906001600160a01b031633148061045957506006546001600160a01b031633145b151561046457600080fd5b506006546001600160a01b031690565b6002546001600160a01b031690565b600554600090815260086020908152604090912082516104a59284019061083d565b505060058054600090815260096020526040902080546001600160a01b0319163317905580546001019055565b6001805460045460038054604080516020600261010098851615989098026000190190931696909604601f810183900483028701830190915280865260009560609587956001600160a01b039091169493909284918301828280156105785780601f1061054d57610100808354040283529160200191610578565b820191906000526020600020905b81548152906001019060200180831161055b57829003601f168201915b50505050509150925092509250909192565b6001546001600160a01b031690565b6000805460408051600160e11b6351cfd60902815290516060936001600160a01b039093169263a39fac129260048082019391829003018186803b1580156105e057600080fd5b505afa1580156105f4573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561061d57600080fd5b81019080805164010000000081111561063557600080fd5b8201602081018481111561064857600080fd5b815185602082028301116401000000008211171561066557600080fd5b509094505050505090565b6001546000906001600160a01b0316331461068a57600080fd5b6002546001600160a01b031615156106aa576001546001600160a01b0316ff5b506000610430565b6001546060906000906001600160a01b031633146106cf57600080fd5b600083815260086020908152604080832060098352928190205483548251601f600260001960018516156101000201909316929092049182018590048502810185019093528083526001600160a01b03909116928491908301828280156107775780601f1061074c57610100808354040283529160200191610777565b820191906000526020600020905b81548152906001019060200180831161075a57829003601f168201915b5050505050915091509150915091565b346004541480156107a257506006546001600160a01b031633145b15156107ad57600080fd5b600280546001600160a01b031916331790556001546004546040516001600160a01b03929092169181156108fc0291906000818181858888f193505050501580156107fc573d6000803e3d6000fd5b5060408051338152346020820152428183015290517f0b1bfe71e28a567ff82336ff0d85916a7b5ccc831c26106043451f582381ff849181900360600190a1565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061087e57805160ff19168380011785556108ab565b828001600101855582156108ab579182015b828111156108ab578251825591602001919060010190610890565b506108b79291506108bb565b5090565b61043091905b808211156108b757600081556001016108c156fea165627a7a72305820b2ebe0caa1806a56fdec651a1618c8d06ffcf42f41465fdb926cf6f1e927ab9d0029";

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

    public RemoteCall<TransactionReceipt> setAllowerdBuyer(String allowedAddress, BigInteger weiValue) {
        final Function function = new Function(
                "setAllowerdBuyer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(allowedAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> getBidCount() {
        final Function function = new Function("getBidCount", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getAllowedBuyer() {
        final Function function = new Function("getAllowedBuyer", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getBuyer() {
        final Function function = new Function("getBuyer", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addBid(String bidData, BigInteger weiValue) {
        final Function function = new Function(
                "addBid", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(bidData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
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

    public RemoteCall<Tuple2<String, String>> getBidAtPosition(BigInteger position) {
        final Function function = new Function("getBidAtPosition", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Int256(position)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
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
