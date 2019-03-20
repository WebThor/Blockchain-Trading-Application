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
import org.web3j.abi.datatypes.Bool;
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
import org.web3j.tuples.generated.Tuple7;
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
    private static final String BINARY = "608060405260405162000b1e38038062000b1e83398101806040526101408110156200002a57600080fd5b81516020830151604084018051929491938201926401000000008111156200005157600080fd5b820160208101848111156200006557600080fd5b81516401000000008111828201871017156200008057600080fd5b505092919060200180516401000000008111156200009d57600080fd5b82016020810184811115620000b157600080fd5b8151640100000000811182820187101715620000cc57600080fd5b50509291906020018051640100000000811115620000e957600080fd5b82016020810184811115620000fd57600080fd5b81516401000000008111828201871017156200011857600080fd5b505092919060200180516401000000008111156200013557600080fd5b820160208101848111156200014957600080fd5b81516401000000008111828201871017156200016457600080fd5b505060208201516040830151606090930180519295919491926401000000008111156200019057600080fd5b82016020810184811115620001a457600080fd5b8151640100000000811182820187101715620001bf57600080fd5b50509291906020018051640100000000811115620001dc57600080fd5b82016020810184811115620001f057600080fd5b81516401000000008111828201871017156200020b57600080fd5b505092919050505033600160006101000a8154816001600160a01b0302191690836001600160a01b03160217905550896000806101000a8154816001600160a01b0302191690836001600160a01b031602179055506000809054906101000a90046001600160a01b03166001600160a01b03166338eada1c306040518263ffffffff1660e01b815260040180826001600160a01b03166001600160a01b03168152602001915050600060405180830381600087803b158015620002cd57600080fd5b505af1158015620002e2573d6000803e3d6000fd5b5050600280548c15157401000000000000000000000000000000000000000002600160a01b60ff02199091161790555050600380546001600160a01b0319163317905587516200033a9060049060208b0190620003c3565b508651620003509060059060208a0190620003c3565b50855162000366906006906020890190620003c3565b5084516200037c906007906020880190620003c3565b506008849055600983905581516200039c90600a906020850190620003c3565b508051620003b290600b906020840190620003c3565b505050505050505050505062000468565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200040657805160ff191683800117855562000436565b8280016001018555821562000436579182015b828111156200043657825182559160200191906001019062000419565b506200044492915062000448565b5090565b6200046591905b808211156200044457600081556001016200044f565b90565b6106a680620004786000396000f3fe6080604052600436106100555760003560e01c8063603daf9a1461005a5780636ac06c7d1461008b578063893d20e814610216578063a39fac121461022b578063b053234914610290578063d835293e1461029a575b600080fd5b34801561006657600080fd5b5061006f6102a2565b604080516001600160a01b039092168252519081900360200190f35b34801561009757600080fd5b506100a06102b1565b6040518088151515158152602001876001600160a01b03166001600160a01b03168152602001806020018681526020018581526020018060200180602001848103845289818151815260200191508051906020019080838360005b838110156101135781810151838201526020016100fb565b50505050905090810190601f1680156101405780820380516001836020036101000a031916815260200191505b50848103835286518152865160209182019188019080838360005b8381101561017357818101518382015260200161015b565b50505050905090810190601f1680156101a05780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b838110156101d35781810151838201526020016101bb565b50505050905090810190601f1680156102005780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b34801561022257600080fd5b5061006f6104bd565b34801561023757600080fd5b506102406104cc565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561027c578181015183820152602001610264565b505050509050019250505060405180910390f35b6102986105a3565b005b6102986105dc565b6002546001600160a01b031690565b6000806060600080606080600260149054906101000a900460ff16600360009054906101000a90046001600160a01b03166004600854600954600a600b848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103835780601f1061035857610100808354040283529160200191610383565b820191906000526020600020905b81548152906001019060200180831161036657829003601f168201915b5050855460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959a50879450925084019050828280156104115780601f106103e657610100808354040283529160200191610411565b820191906000526020600020905b8154815290600101906020018083116103f457829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529597508694509250840190508282801561049f5780601f106104745761010080835404028352916020019161049f565b820191906000526020600020905b81548152906001019060200180831161048257829003601f168201915b50505050509050965096509650965096509650965090919293949596565b6001546001600160a01b031690565b6000805460408051600160e11b6351cfd60902815290516060936001600160a01b039093169263a39fac129260048082019391829003018186803b15801561051357600080fd5b505afa158015610527573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561055057600080fd5b81019080805164010000000081111561056857600080fd5b8201602081018481111561057b57600080fd5b815185602082028301116401000000008211171561059857600080fd5b509094505050505090565b6001546001600160a01b031633146105ba57600080fd5b6002546001600160a01b031615156105da576001546001600160a01b0316ff5b565b60095434146105ea57600080fd5b600280546001600160a01b031916331790556001546009546040516001600160a01b03929092169181156108fc0291906000818181858888f19350505050158015610639573d6000803e3d6000fd5b5060408051338152346020820152428183015290517f0b1bfe71e28a567ff82336ff0d85916a7b5ccc831c26106043451f582381ff849181900360600190a156fea165627a7a72305820cdb6052d5acd8b695b98e281993cd93266be861227cedd133f54fc5a4eae69eb0029";

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

    public RemoteCall<Tuple7<Boolean, String, String, BigInteger, BigInteger, String, String>> returnContractDetails() {
        final Function function = new Function("returnContractDetails", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple7<Boolean, String, String, BigInteger, BigInteger, String, String>>(
                new Callable<Tuple7<Boolean, String, String, BigInteger, BigInteger, String, String>>() {
                    @Override
                    public Tuple7<Boolean, String, String, BigInteger, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<Boolean, String, String, BigInteger, BigInteger, String, String>(
                                (Boolean) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (String) results.get(6).getValue());
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

    public static RemoteCall<Deal> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _addressBook, Boolean _isOffer, String _metaData, String _ageGroup, String _gender, String _education, BigInteger _duration, BigInteger _price, String _typeOfData, String _gateKeeper) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_addressBook), 
                new org.web3j.abi.datatypes.Bool(_isOffer), 
                new org.web3j.abi.datatypes.Utf8String(_metaData), 
                new org.web3j.abi.datatypes.Utf8String(_ageGroup), 
                new org.web3j.abi.datatypes.Utf8String(_gender), 
                new org.web3j.abi.datatypes.Utf8String(_education), 
                new org.web3j.abi.datatypes.generated.Uint256(_duration), 
                new org.web3j.abi.datatypes.generated.Uint256(_price), 
                new org.web3j.abi.datatypes.Utf8String(_typeOfData), 
                new org.web3j.abi.datatypes.Utf8String(_gateKeeper)));
        return deployRemoteCall(Deal.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<Deal> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _addressBook, Boolean _isOffer, String _metaData, String _ageGroup, String _gender, String _education, BigInteger _duration, BigInteger _price, String _typeOfData, String _gateKeeper) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_addressBook), 
                new org.web3j.abi.datatypes.Bool(_isOffer), 
                new org.web3j.abi.datatypes.Utf8String(_metaData), 
                new org.web3j.abi.datatypes.Utf8String(_ageGroup), 
                new org.web3j.abi.datatypes.Utf8String(_gender), 
                new org.web3j.abi.datatypes.Utf8String(_education), 
                new org.web3j.abi.datatypes.generated.Uint256(_duration), 
                new org.web3j.abi.datatypes.generated.Uint256(_price), 
                new org.web3j.abi.datatypes.Utf8String(_typeOfData), 
                new org.web3j.abi.datatypes.Utf8String(_gateKeeper)));
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
