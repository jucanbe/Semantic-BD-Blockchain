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
import org.web3j.tuples.generated.Tuple5;
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
public class IoT_Temp extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610be0806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806327e296bc1461004657806366970e6c1461007a578063b3eb7fa514610096575b600080fd5b610060600480360381019061005b91906104c4565b6100ca565b60405161007195949392919061059a565b60405180910390f35b610094600480360381019061008f919061075c565b610210565b005b6100b060048036038101906100ab9190610821565b61030c565b6040516100c195949392919061059a565b60405180910390f35b60016020528060005260406000206000915090508060000180546100ed9061087d565b80601f01602080910402602001604051908101604052809291908181526020018280546101199061087d565b80156101665780601f1061013b57610100808354040283529160200191610166565b820191906000526020600020905b81548152906001019060200180831161014957829003601f168201915b50505050509080600101805461017b9061087d565b80601f01602080910402602001604051908101604052809291908181526020018280546101a79061087d565b80156101f45780601f106101c9576101008083540402835291602001916101f4565b820191906000526020600020905b8154815290600101906020018083116101d757829003601f168201915b5050505050908060020154908060030154908060040154905085565b6040518060a0016040528086815260200185815260200184815260200183815260200182815250600160008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082015181600001908161028b9190610a5a565b5060208201518160010190816102a19190610a5a565b506040820151816002015560608201518160030155608082015181600401559050507fa8f1054471e9466f1ccbda85756fe10dc6cabe795f139c448773dece53d4a8688686868686866040516102fc96959493929190610b3b565b60405180910390a1505050505050565b600060205280600052604060002060009150905080600001805461032f9061087d565b80601f016020809104026020016040519081016040528092919081815260200182805461035b9061087d565b80156103a85780601f1061037d576101008083540402835291602001916103a8565b820191906000526020600020905b81548152906001019060200180831161038b57829003601f168201915b5050505050908060010180546103bd9061087d565b80601f01602080910402602001604051908101604052809291908181526020018280546103e99061087d565b80156104365780601f1061040b57610100808354040283529160200191610436565b820191906000526020600020905b81548152906001019060200180831161041957829003601f168201915b5050505050908060020154908060030154908060040154905085565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061049182610466565b9050919050565b6104a181610486565b81146104ac57600080fd5b50565b6000813590506104be81610498565b92915050565b6000602082840312156104da576104d961045c565b5b60006104e8848285016104af565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561052b578082015181840152602081019050610510565b60008484015250505050565b6000601f19601f8301169050919050565b6000610553826104f1565b61055d81856104fc565b935061056d81856020860161050d565b61057681610537565b840191505092915050565b6000819050919050565b61059481610581565b82525050565b600060a08201905081810360008301526105b48188610548565b905081810360208301526105c88187610548565b90506105d7604083018661058b565b6105e4606083018561058b565b6105f1608083018461058b565b9695505050505050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61063d82610537565b810181811067ffffffffffffffff8211171561065c5761065b610605565b5b80604052505050565b600061066f610452565b905061067b8282610634565b919050565b600067ffffffffffffffff82111561069b5761069a610605565b5b6106a482610537565b9050602081019050919050565b82818337600083830152505050565b60006106d36106ce84610680565b610665565b9050828152602081018484840111156106ef576106ee610600565b5b6106fa8482856106b1565b509392505050565b600082601f830112610717576107166105fb565b5b81356107278482602086016106c0565b91505092915050565b61073981610581565b811461074457600080fd5b50565b60008135905061075681610730565b92915050565b60008060008060008060c087890312156107795761077861045c565b5b600061078789828a016104af565b965050602087013567ffffffffffffffff8111156107a8576107a7610461565b5b6107b489828a01610702565b955050604087013567ffffffffffffffff8111156107d5576107d4610461565b5b6107e189828a01610702565b94505060606107f289828a01610747565b935050608061080389828a01610747565b92505060a061081489828a01610747565b9150509295509295509295565b6000602082840312156108375761083661045c565b5b600061084584828501610747565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061089557607f821691505b6020821081036108a8576108a761084e565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026109107fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826108d3565b61091a86836108d3565b95508019841693508086168417925050509392505050565b6000819050919050565b600061095761095261094d84610581565b610932565b610581565b9050919050565b6000819050919050565b6109718361093c565b61098561097d8261095e565b8484546108e0565b825550505050565b600090565b61099a61098d565b6109a5818484610968565b505050565b5b818110156109c9576109be600082610992565b6001810190506109ab565b5050565b601f821115610a0e576109df816108ae565b6109e8846108c3565b810160208510156109f7578190505b610a0b610a03856108c3565b8301826109aa565b50505b505050565b600082821c905092915050565b6000610a3160001984600802610a13565b1980831691505092915050565b6000610a4a8383610a20565b9150826002028217905092915050565b610a63826104f1565b67ffffffffffffffff811115610a7c57610a7b610605565b5b610a86825461087d565b610a918282856109cd565b600060209050601f831160018114610ac45760008415610ab2578287015190505b610abc8582610a3e565b865550610b24565b601f198416610ad2866108ae565b60005b82811015610afa57848901518255600182019150602085019450602081019050610ad5565b86831015610b175784890151610b13601f891682610a20565b8355505b6001600288020188555050505b505050505050565b610b3581610486565b82525050565b600060c082019050610b506000830189610b2c565b8181036020830152610b628188610548565b90508181036040830152610b768187610548565b9050610b85606083018661058b565b610b92608083018561058b565b610b9f60a083018461058b565b97965050505050505056fea2646970667358221220a944cb6a3d306c47ceba7ef62b0061c7feb185cfaf6115b5bf2d6acc0b7e2d7064736f6c63430008100033";

    public static final String FUNC_DEVICEINFO = "deviceInfo";

    public static final String FUNC_STUDENTNAMES = "studentNames";

    public static final String FUNC_STOREDEVICESTATUS = "storeDeviceStatus";

    public static final Event MYEVENT_EVENT = new Event("MyEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x64F53D742ef261eD7A01AF0F802998B2e530e0f4");
    }

    @Deprecated
    protected IoT_Temp(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IoT_Temp(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IoT_Temp(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IoT_Temp(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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
            typedResponse.temp_min = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.temp_max = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
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
                typedResponse.temp_min = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.temp_max = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MyEventEventResponse> myEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MYEVENT_EVENT));
        return myEventEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, BigInteger>> deviceInfo(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEVICEINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<String, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<String, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, BigInteger>> studentNames(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STUDENTNAMES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<String, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<String, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> storeDeviceStatus(String ID, String ontology_dir, String indentifier, BigInteger temp_min, BigInteger temp_max, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STOREDEVICESTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID), 
                new org.web3j.abi.datatypes.Utf8String(ontology_dir), 
                new org.web3j.abi.datatypes.Utf8String(indentifier), 
                new org.web3j.abi.datatypes.generated.Uint256(temp_min), 
                new org.web3j.abi.datatypes.generated.Uint256(temp_max), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IoT_Temp load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IoT_Temp(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IoT_Temp load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IoT_Temp(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IoT_Temp load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IoT_Temp(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IoT_Temp load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IoT_Temp(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IoT_Temp> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IoT_Temp.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IoT_Temp> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IoT_Temp.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IoT_Temp> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IoT_Temp.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IoT_Temp> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IoT_Temp.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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

        public BigInteger temp_min;

        public BigInteger temp_max;

        public BigInteger timestamp;
    }
}
