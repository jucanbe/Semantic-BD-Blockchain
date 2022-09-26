package oeg.upm.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 5.0.0.
 */
@SuppressWarnings("rawtypes")
public class Sensor_IoT extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610e2b806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806327e296bc1461003b5780639d1d726014610074575b600080fd5b6100556004803603810190610050919061057f565b610090565b60405161006b9a9998979695949392919061066e565b60405180910390f35b61008e600480360381019061008991906108ba565b61038c565b005b60006020528060005260406000206000915090508060000180546100b390610a69565b80601f01602080910402602001604051908101604052809291908181526020018280546100df90610a69565b801561012c5780601f106101015761010080835404028352916020019161012c565b820191906000526020600020905b81548152906001019060200180831161010f57829003601f168201915b50505050509080600101805461014190610a69565b80601f016020809104026020016040519081016040528092919081815260200182805461016d90610a69565b80156101ba5780601f1061018f576101008083540402835291602001916101ba565b820191906000526020600020905b81548152906001019060200180831161019d57829003601f168201915b5050505050908060020180546101cf90610a69565b80601f01602080910402602001604051908101604052809291908181526020018280546101fb90610a69565b80156102485780601f1061021d57610100808354040283529160200191610248565b820191906000526020600020905b81548152906001019060200180831161022b57829003601f168201915b50505050509080600301805461025d90610a69565b80601f016020809104026020016040519081016040528092919081815260200182805461028990610a69565b80156102d65780601f106102ab576101008083540402835291602001916102d6565b820191906000526020600020905b8154815290600101906020018083116102b957829003601f168201915b5050505050908060040180546102eb90610a69565b80601f016020809104026020016040519081016040528092919081815260200182805461031790610a69565b80156103645780601f1061033957610100808354040283529160200191610364565b820191906000526020600020905b81548152906001019060200180831161034757829003601f168201915b505050505090806005015490806006015490806007015490806008015490806009015490508a565b6040518061014001604052808b81526020018a8152602001898152602001888152602001878152602001868152602001858152602001848152602001838152602001828152506000808d73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190816104259190610c46565b50602082015181600101908161043b9190610c46565b5060408201518160020190816104519190610c46565b5060608201518160030190816104679190610c46565b50608082015181600401908161047d9190610c46565b5060a0820151816005015560c0820151816006015560e08201518160070155610100820151816008015561012082015181600901559050507f90d1fdbcf838efb09bed473ab189197afd86e77954221413b8c7b936d39e733b8b8b8b8b8b8b8b8b8b8b8b6040516104f89b9a99989796959493929190610d27565b60405180910390a15050505050505050505050565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061054c82610521565b9050919050565b61055c81610541565b811461056757600080fd5b50565b60008135905061057981610553565b92915050565b60006020828403121561059557610594610517565b5b60006105a38482850161056a565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156105e65780820151818401526020810190506105cb565b60008484015250505050565b6000601f19601f8301169050919050565b600061060e826105ac565b61061881856105b7565b93506106288185602086016105c8565b610631816105f2565b840191505092915050565b6000819050919050565b61064f8161063c565b82525050565b6000819050919050565b61066881610655565b82525050565b6000610140820190508181036000830152610689818d610603565b9050818103602083015261069d818c610603565b905081810360408301526106b1818b610603565b905081810360608301526106c5818a610603565b905081810360808301526106d98189610603565b90506106e860a0830188610646565b6106f560c0830187610646565b61070260e0830186610646565b610710610100830185610646565b61071e61012083018461065f565b9b9a5050505050505050505050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61076f826105f2565b810181811067ffffffffffffffff8211171561078e5761078d610737565b5b80604052505050565b60006107a161050d565b90506107ad8282610766565b919050565b600067ffffffffffffffff8211156107cd576107cc610737565b5b6107d6826105f2565b9050602081019050919050565b82818337600083830152505050565b6000610805610800846107b2565b610797565b90508281526020810184848401111561082157610820610732565b5b61082c8482856107e3565b509392505050565b600082601f8301126108495761084861072d565b5b81356108598482602086016107f2565b91505092915050565b61086b8161063c565b811461087657600080fd5b50565b60008135905061088881610862565b92915050565b61089781610655565b81146108a257600080fd5b50565b6000813590506108b48161088e565b92915050565b60008060008060008060008060008060006101608c8e0312156108e0576108df610517565b5b60006108ee8e828f0161056a565b9b505060208c013567ffffffffffffffff81111561090f5761090e61051c565b5b61091b8e828f01610834565b9a505060408c013567ffffffffffffffff81111561093c5761093b61051c565b5b6109488e828f01610834565b99505060608c013567ffffffffffffffff8111156109695761096861051c565b5b6109758e828f01610834565b98505060808c013567ffffffffffffffff8111156109965761099561051c565b5b6109a28e828f01610834565b97505060a08c013567ffffffffffffffff8111156109c3576109c261051c565b5b6109cf8e828f01610834565b96505060c06109e08e828f01610879565b95505060e06109f18e828f01610879565b945050610100610a038e828f01610879565b935050610120610a158e828f01610879565b925050610140610a278e828f016108a5565b9150509295989b509295989b9093969950565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610a8157607f821691505b602082108103610a9457610a93610a3a565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302610afc7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610abf565b610b068683610abf565b95508019841693508086168417925050509392505050565b6000819050919050565b6000610b43610b3e610b398461063c565b610b1e565b61063c565b9050919050565b6000819050919050565b610b5d83610b28565b610b71610b6982610b4a565b848454610acc565b825550505050565b600090565b610b86610b79565b610b91818484610b54565b505050565b5b81811015610bb557610baa600082610b7e565b600181019050610b97565b5050565b601f821115610bfa57610bcb81610a9a565b610bd484610aaf565b81016020851015610be3578190505b610bf7610bef85610aaf565b830182610b96565b50505b505050565b600082821c905092915050565b6000610c1d60001984600802610bff565b1980831691505092915050565b6000610c368383610c0c565b9150826002028217905092915050565b610c4f826105ac565b67ffffffffffffffff811115610c6857610c67610737565b5b610c728254610a69565b610c7d828285610bb9565b600060209050601f831160018114610cb05760008415610c9e578287015190505b610ca88582610c2a565b865550610d10565b601f198416610cbe86610a9a565b60005b82811015610ce657848901518255600182019150602085019450602081019050610cc1565b86831015610d035784890151610cff601f891682610c0c565b8355505b6001600288020188555050505b505050505050565b610d2181610541565b82525050565b600061016082019050610d3d600083018e610d18565b8181036020830152610d4f818d610603565b90508181036040830152610d63818c610603565b90508181036060830152610d77818b610603565b90508181036080830152610d8b818a610603565b905081810360a0830152610d9f8189610603565b9050610dae60c0830188610646565b610dbb60e0830187610646565b610dc9610100830186610646565b610dd7610120830185610646565b610de561014083018461065f565b9c9b50505050505050505050505056fea264697066735822122061b9dabef1b44bbe922926b79a39311bca331039156feaedbeb19c950c9f7b0b64736f6c63430008100033";

    public static final String FUNC_DEVICEINFO = "deviceInfo";

    public static final String FUNC_STOREDEVICESTATUS = "storeDeviceStatus";

    public static final Event MYEVENT_EVENT = new Event("MyEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x954c986b38f35FD7D7cBeA21134159ff1465596f");
    }

    @Deprecated
    protected Sensor_IoT(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Sensor_IoT(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Sensor_IoT(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Sensor_IoT(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<MyEventEventResponse> getMyEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MYEVENT_EVENT, transactionReceipt);
        ArrayList<MyEventEventResponse> responses = new ArrayList<MyEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MyEventEventResponse typedResponse = new MyEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ontology_dir = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.indentifier = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.buildingName = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.location = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.office = (String) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.lux = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
            typedResponse.co2 = (BigInteger) eventValues.getNonIndexedValues().get(8).getValue();
            typedResponse.humidity = (BigInteger) eventValues.getNonIndexedValues().get(9).getValue();
            typedResponse.temp = (BigInteger) eventValues.getNonIndexedValues().get(10).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MyEventEventResponse> myEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MyEventEventResponse>() {
            @Override
            public MyEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MYEVENT_EVENT, log);
                MyEventEventResponse typedResponse = new MyEventEventResponse();
                typedResponse.log = log;
                typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.ontology_dir = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.indentifier = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.buildingName = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.location = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.office = (String) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                typedResponse.lux = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
                typedResponse.co2 = (BigInteger) eventValues.getNonIndexedValues().get(8).getValue();
                typedResponse.humidity = (BigInteger) eventValues.getNonIndexedValues().get(9).getValue();
                typedResponse.temp = (BigInteger) eventValues.getNonIndexedValues().get(10).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MyEventEventResponse> myEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MYEVENT_EVENT));
        return myEventEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple10<String, String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> deviceInfo(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEVICEINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}));
        return new RemoteFunctionCall<Tuple10<String, String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple10<String, String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple10<String, String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<String, String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue(), 
                                (BigInteger) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> storeDeviceStatus(String ID, String ontology_dir, String indentifier, String buildingName, String location, String office, BigInteger timestamp, BigInteger lux, BigInteger co2, BigInteger humidity, BigInteger temp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STOREDEVICESTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID), 
                new org.web3j.abi.datatypes.Utf8String(ontology_dir), 
                new org.web3j.abi.datatypes.Utf8String(indentifier), 
                new org.web3j.abi.datatypes.Utf8String(buildingName), 
                new org.web3j.abi.datatypes.Utf8String(location), 
                new org.web3j.abi.datatypes.Utf8String(office), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp), 
                new org.web3j.abi.datatypes.generated.Uint256(lux), 
                new org.web3j.abi.datatypes.generated.Uint256(co2), 
                new org.web3j.abi.datatypes.generated.Uint256(humidity), 
                new org.web3j.abi.datatypes.generated.Int256(temp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Sensor_IoT load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Sensor_IoT(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Sensor_IoT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Sensor_IoT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Sensor_IoT load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Sensor_IoT(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Sensor_IoT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Sensor_IoT(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Sensor_IoT> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Sensor_IoT.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Sensor_IoT> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Sensor_IoT.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Sensor_IoT> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Sensor_IoT.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Sensor_IoT> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Sensor_IoT.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class MyEventEventResponse extends BaseEventResponse {
        public String ID;

        public String ontology_dir;

        public String indentifier;

        public String buildingName;

        public String location;

        public String office;

        public BigInteger timestamp;

        public BigInteger lux;

        public BigInteger co2;

        public BigInteger humidity;

        public BigInteger temp;
    }
}
