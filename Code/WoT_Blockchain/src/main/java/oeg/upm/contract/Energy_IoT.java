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
import org.web3j.tuples.generated.Tuple6;
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
public class Energy_IoT extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610bf4806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80631a06b6611461003b5780635956796d14610057575b600080fd5b610055600480360381019061005091906105fc565b61008c565b005b610071600480360381019061006c919061070e565b6101b2565b604051610083969594939291906107c9565b60405180910390f35b6040518060c00160405280878152602001868152602001858152602001848152602001838152602001828152506000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082015181600001908161010c9190610a52565b5060208201518160010190816101229190610a52565b5060408201518160020190816101389190610a52565b50606082015181600301908161014e9190610a52565b506080820151816004015560a082015181600501559050507fe381dea1c9516722ea00e6b8844b802dfcd5c4c873adcae38ec0e5701221114e878787878787876040516101a19796959493929190610b33565b60405180910390a150505050505050565b60006020528060005260406000206000915090508060000180546101d590610875565b80601f016020809104026020016040519081016040528092919081815260200182805461020190610875565b801561024e5780601f106102235761010080835404028352916020019161024e565b820191906000526020600020905b81548152906001019060200180831161023157829003601f168201915b50505050509080600101805461026390610875565b80601f016020809104026020016040519081016040528092919081815260200182805461028f90610875565b80156102dc5780601f106102b1576101008083540402835291602001916102dc565b820191906000526020600020905b8154815290600101906020018083116102bf57829003601f168201915b5050505050908060020180546102f190610875565b80601f016020809104026020016040519081016040528092919081815260200182805461031d90610875565b801561036a5780601f1061033f5761010080835404028352916020019161036a565b820191906000526020600020905b81548152906001019060200180831161034d57829003601f168201915b50505050509080600301805461037f90610875565b80601f01602080910402602001604051908101604052809291908181526020018280546103ab90610875565b80156103f85780601f106103cd576101008083540402835291602001916103f8565b820191906000526020600020905b8154815290600101906020018083116103db57829003601f168201915b5050505050908060040154908060050154905086565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061044d82610422565b9050919050565b61045d81610442565b811461046857600080fd5b50565b60008135905061047a81610454565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6104d38261048a565b810181811067ffffffffffffffff821117156104f2576104f161049b565b5b80604052505050565b600061050561040e565b905061051182826104ca565b919050565b600067ffffffffffffffff8211156105315761053061049b565b5b61053a8261048a565b9050602081019050919050565b82818337600083830152505050565b600061056961056484610516565b6104fb565b90508281526020810184848401111561058557610584610485565b5b610590848285610547565b509392505050565b600082601f8301126105ad576105ac610480565b5b81356105bd848260208601610556565b91505092915050565b6000819050919050565b6105d9816105c6565b81146105e457600080fd5b50565b6000813590506105f6816105d0565b92915050565b600080600080600080600060e0888a03121561061b5761061a610418565b5b60006106298a828b0161046b565b975050602088013567ffffffffffffffff81111561064a5761064961041d565b5b6106568a828b01610598565b965050604088013567ffffffffffffffff8111156106775761067661041d565b5b6106838a828b01610598565b955050606088013567ffffffffffffffff8111156106a4576106a361041d565b5b6106b08a828b01610598565b945050608088013567ffffffffffffffff8111156106d1576106d061041d565b5b6106dd8a828b01610598565b93505060a06106ee8a828b016105e7565b92505060c06106ff8a828b016105e7565b91505092959891949750929550565b60006020828403121561072457610723610418565b5b60006107328482850161046b565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561077557808201518184015260208101905061075a565b60008484015250505050565b600061078c8261073b565b6107968185610746565b93506107a6818560208601610757565b6107af8161048a565b840191505092915050565b6107c3816105c6565b82525050565b600060c08201905081810360008301526107e38189610781565b905081810360208301526107f78188610781565b9050818103604083015261080b8187610781565b9050818103606083015261081f8186610781565b905061082e60808301856107ba565b61083b60a08301846107ba565b979650505050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061088d57607f821691505b6020821081036108a05761089f610846565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026109087fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826108cb565b61091286836108cb565b95508019841693508086168417925050509392505050565b6000819050919050565b600061094f61094a610945846105c6565b61092a565b6105c6565b9050919050565b6000819050919050565b61096983610934565b61097d61097582610956565b8484546108d8565b825550505050565b600090565b610992610985565b61099d818484610960565b505050565b5b818110156109c1576109b660008261098a565b6001810190506109a3565b5050565b601f821115610a06576109d7816108a6565b6109e0846108bb565b810160208510156109ef578190505b610a036109fb856108bb565b8301826109a2565b50505b505050565b600082821c905092915050565b6000610a2960001984600802610a0b565b1980831691505092915050565b6000610a428383610a18565b9150826002028217905092915050565b610a5b8261073b565b67ffffffffffffffff811115610a7457610a7361049b565b5b610a7e8254610875565b610a898282856109c5565b600060209050601f831160018114610abc5760008415610aaa578287015190505b610ab48582610a36565b865550610b1c565b601f198416610aca866108a6565b60005b82811015610af257848901518255600182019150602085019450602081019050610acd565b86831015610b0f5784890151610b0b601f891682610a18565b8355505b6001600288020188555050505b505050505050565b610b2d81610442565b82525050565b600060e082019050610b48600083018a610b24565b8181036020830152610b5a8189610781565b90508181036040830152610b6e8188610781565b90508181036060830152610b828187610781565b90508181036080830152610b968186610781565b9050610ba560a08301856107ba565b610bb260c08301846107ba565b9897505050505050505056fea26469706673582212203b25b519bc7c268dad01a0148f5aac286889c5f422a05cd1d5b03328cbc2399d64736f6c63430008100033";

    public static final String FUNC_ENERGYINFO = "energyInfo";

    public static final String FUNC_STOREDEVICESTATUS = "storeDeviceStatus";

    public static final Event MYEVENT_EVENT = new Event("MyEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x40c3bcF4cd5b555AF05a71B21CB4809345B8d48e");
    }

    @Deprecated
    protected Energy_IoT(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Energy_IoT(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Energy_IoT(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Energy_IoT(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.energy = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
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
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.energy = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MyEventEventResponse> myEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MYEVENT_EVENT));
        return myEventEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple6<String, String, String, String, BigInteger, BigInteger>> energyInfo(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ENERGYINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, String, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<String, String, String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, String, String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> storeDeviceStatus(String ID, String ontology_dir, String indentifier, String buildingName, String location, BigInteger timestamp, BigInteger energy) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STOREDEVICESTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ID), 
                new org.web3j.abi.datatypes.Utf8String(ontology_dir), 
                new org.web3j.abi.datatypes.Utf8String(indentifier), 
                new org.web3j.abi.datatypes.Utf8String(buildingName), 
                new org.web3j.abi.datatypes.Utf8String(location), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp), 
                new org.web3j.abi.datatypes.generated.Uint256(energy)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Energy_IoT load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Energy_IoT(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Energy_IoT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Energy_IoT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Energy_IoT load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Energy_IoT(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Energy_IoT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Energy_IoT(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Energy_IoT> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Energy_IoT.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Energy_IoT> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Energy_IoT.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Energy_IoT> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Energy_IoT.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Energy_IoT> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Energy_IoT.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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

        public BigInteger timestamp;

        public BigInteger energy;
    }
}
