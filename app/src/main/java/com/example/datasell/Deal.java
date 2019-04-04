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
    private static final String BINARY = "60806040526000600555604051610e40380380610e408339810180604052606081101561002b57600080fd5b81516020830180519193928301929164010000000081111561004c57600080fd5b8201602081018481111561005f57600080fd5b815164010000000081118282018710171561007957600080fd5b5050602090910151600180546001600160a01b03199081163317909155600080546001600160a01b038881169190931617808255604080517f38eada1c0000000000000000000000000000000000000000000000000000000081523060048201529051959750939550909116926338eada1c92602480820193929182900301818387803b15801561010957600080fd5b505af115801561011d573d6000803e3d6000fd5b505083516101349250600391506020850190610140565b50600455506101db9050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061018157805160ff19168380011785556101ae565b828001600101855582156101ae579182015b828111156101ae578251825591602001919060010190610193565b506101ba9291506101be565b5090565b6101d891905b808211156101ba57600081556001016101c4565b90565b610c56806101ea6000396000f3fe6080604052600436106100a75760003560e01c8063893d20e811610064578063893d20e81461028a578063a39fac121461029f578063b053234914610304578063c0f0114114610320578063d3625608146103db578063d835293e14610401576100a7565b806304892b1d146100ac57806328f6a48a146100d45780632c780149146100fb578063603daf9a1461012c57806363aeecc1146101415780636ac06c7d146101e7575b600080fd5b6100d2600480360360208110156100c257600080fd5b50356001600160a01b0316610409565b005b3480156100e057600080fd5b506100e9610442565b60408051918252519081900360200190f35b34801561010757600080fd5b50610110610464565b604080516001600160a01b039092168252519081900360200190f35b34801561013857600080fd5b506101106104a5565b6100d26004803603602081101561015757600080fd5b81019060208101813564010000000081111561017257600080fd5b82018360208201111561018457600080fd5b803590602001918460018302840111640100000000831117156101a657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506104b4945050505050565b3480156101f357600080fd5b506101fc610541565b604080516001600160a01b0385168152908101829052606060208083018281528551928401929092528451608084019186019080838360005b8381101561024d578181015183820152602001610235565b50505050905090810190601f16801561027a5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561029657600080fd5b506101106105f9565b3480156102ab57600080fd5b506102b4610608565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156102f05781810151838201526020016102d8565b505050509050019250505060405180910390f35b61030c6106df565b604080519115158252519081900360200190f35b34801561032c57600080fd5b5061034a6004803603602081101561034357600080fd5b5035610721565b6040518080602001836001600160a01b03166001600160a01b03168152602001828103825284818151815260200191508051906020019080838360005b8381101561039f578181015183820152602001610387565b50505050905090810190601f1680156103cc5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b6100d2600480360360208110156103f157600080fd5b50356001600160a01b03166107f6565b6100d26109d5565b6001546001600160a01b0316331461042057600080fd5b600680546001600160a01b0319166001600160a01b0392909216919091179055565b6001546000906001600160a01b0316331461045c57600080fd5b506005545b90565b6001546000906001600160a01b031633148061048a57506006546001600160a01b031633145b151561049557600080fd5b506006546001600160a01b031690565b6002546001600160a01b031690565b60006104bf33610a8b565b905080600019141561051c57600554600090815260086020908152604090912083516104ed92850190610ad6565b5060058054600090815260096020526040902080546001600160a01b031916331790558054600101905561053d565b6000818152600860209081526040909120835161053b92850190610ad6565b505b5050565b6001805460045460038054604080516020600261010098851615989098026000190190931696909604601f810183900483028701830190915280865260009560609587956001600160a01b039091169493909284918301828280156105e75780601f106105bc576101008083540402835291602001916105e7565b820191906000526020600020905b8154815290600101906020018083116105ca57829003601f168201915b50505050509150925092509250909192565b6001546001600160a01b031690565b6000805460408051600160e11b6351cfd60902815290516060936001600160a01b039093169263a39fac129260048082019391829003018186803b15801561064f57600080fd5b505afa158015610663573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561068c57600080fd5b8101908080516401000000008111156106a457600080fd5b820160208101848111156106b757600080fd5b81518560208202830111640100000000821117156106d457600080fd5b509094505050505090565b6001546000906001600160a01b031633146106f957600080fd5b6002546001600160a01b03161515610719576001546001600160a01b0316ff5b506000610461565b6001546060906000906001600160a01b0316331461073e57600080fd5b600083815260086020908152604080832060098352928190205483548251601f600260001960018516156101000201909316929092049182018590048502810185019093528083526001600160a01b03909116928491908301828280156107e65780601f106107bb576101008083540402835291602001916107e6565b820191906000526020600020905b8154815290600101906020018083116107c957829003601f168201915b5050505050915091509150915091565b600061080182610a8b565b6001549091506001600160a01b03163314801561081f575060001981135b151561082a57600080fd5b600081815260096020908152604080832080546001600160a01b03191690556008909152812061085991610b54565b60058054600019908101909155600082815260096020908152604080832054600883529281902080548251600261010060018416150290970190911695909504601f81018490048402860184019092528185526001600160a01b039093169360609390929091908301828280156109115780601f106108e657610100808354040283529160200191610911565b820191906000526020600020905b8154815290600101906020018083116108f457829003601f168201915b5050600580546000908152600960209081526040808320548b845281842080546001600160a01b0319166001600160a01b039092169190911790559254825260089052818120898252919020815495965061098695909450909250600260001960018316156101000201909116049050610b9b565b5060058054600090815260096020908152604080832080546001600160a01b0319166001600160a01b038816179055925482526008815291902082516109ce92840190610ad6565b5050505050565b346004541480156109f057506006546001600160a01b031633145b15156109fb57600080fd5b600280546001600160a01b031916331790556001546004546040516001600160a01b03929092169181156108fc0291906000818181858888f19350505050158015610a4a573d6000803e3d6000fd5b5060408051338152346020820152428183015290517f0b1bfe71e28a567ff82336ff0d85916a7b5ccc831c26106043451f582381ff849181900360600190a1565b6000805b600554811215610aca576000818152600960205260409020546001600160a01b0384811691161415610ac2579050610ad1565b600101610a8f565b5060001990505b919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b1757805160ff1916838001178555610b44565b82800160010185558215610b44579182015b82811115610b44578251825591602001919060010190610b29565b50610b50929150610c10565b5090565b50805460018160011615610100020316600290046000825580601f10610b7a5750610b98565b601f016020900490600052602060002090810190610b989190610c10565b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610bd45780548555610b44565b82800160010185558215610b4457600052602060002091601f016020900482015b82811115610b44578254825591600101919060010190610bf5565b61046191905b80821115610b505760008155600101610c1656fea165627a7a723058206de52eee6d15530fa78324ab5b5d43f7be620d72ba58e52ebbf04fc9d82df4200029";

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

    public RemoteCall<TransactionReceipt> removeBid(String bidder, BigInteger weiValue) {
        final Function function = new Function(
                "removeBid", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(bidder)), 
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
