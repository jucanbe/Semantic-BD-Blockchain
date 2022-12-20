package oeg.upm.WoT_Blockchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

public class ExtractContractValues {

	public static void main( String[] args ){

		Web3j web3j = Web3j.build(new HttpService(Tokens.CHAIN_IP));

		// Event definition
		//		Event MY_EVENT = new Event("MyEvent", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint8>(false) {}));
		Event MY_EVENT = new Event("MyEvent", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Uint256>(true) {}));

		// Event definition hash
		String MY_EVENT_HASH = EventEncoder.encode(MY_EVENT);

		// Filter "0x64F53D742ef261eD7A01AF0F802998B2e530e0f4"
		EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "0xe78A0F7E598Cc8b0Bb87894B0F60dD2a88d6a8Ab");

		// Pull all the events for this contract
		web3j.ethLogFlowable(filter).subscribe(log -> {
			String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash
			System.out.println(eventHash);
			System.out.println(log.getTransactionHash());
			if(eventHash.equals(MY_EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH) 
				//				// address indexed _arg1
//				Address arg1 = (Address) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1), new TypeReference<Address>() {});
//				System.out.println(log.getData());
//				EventValues a;
			    List<Type> eventParam = FunctionReturnDecoder.decode(log.getData(), MY_EVENT.getParameters());
			    System.out.println(eventParam.get(0).getValue());
			    System.out.println(eventParam.get(1).getValue());
			    System.out.println(eventParam.get(2).getValue());
			    System.out.println(eventParam.get(3).getValue());
			    System.out.println(eventParam.get(4).getValue());
			    System.out.println(eventParam.get(5).getValue());

//				Address arg1 = (Address) FunctionReturnDecoder.decodeIndexedValue(log.getData(), new TypeReference<Address>() {});
//				System.out.println(arg1.getValue());
				//				// bytes32 indexed _arg2
//				System.out.println(log.getTopics().size());
//				System.out.println(log.getLogIndex().intValue());
//				Utf8String arg2 = (Utf8String) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1), new TypeReference<Utf8String>() {});
				
				//				// uint8 _arg3
//				Uint8 arg3 = (Uint8) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(3), new TypeReference<Uint8>() {});
//				Uint256 arg3 = (Uint256) FunctionReturnDecoder.decodeIndexedValue(log.getData(), new TypeReference<Uint256>() {});

//				System.out.println(arg3.getValue().intValue());
			}
		});
	}
}
