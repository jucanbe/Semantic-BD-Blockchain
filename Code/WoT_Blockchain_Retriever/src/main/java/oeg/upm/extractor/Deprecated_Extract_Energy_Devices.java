package oeg.upm.extractor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import oeg.upm.extractor.Sensor_IoT.MyEventEventResponse;

public class Deprecated_Extract_Energy_Devices {

	public JsonObject recoverEnergyDevice(String address) {
		Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
		JsonObject finalTempJson = new JsonObject();
		Event MY_EVENT = new Event("MyEvent", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Uint256>(true) {}));

		String MY_EVENT_HASH = EventEncoder.encode(MY_EVENT);

		// Filter
		EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, address);

		// Pull all the events for this contract
		web3j.ethLogFlowable(filter).subscribe(log -> {
			String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash
			System.out.println(eventHash);
			System.out.println(log.getTransactionHash());
			if(eventHash.equals(MY_EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH) 
				List<Type> eventParam = FunctionReturnDecoder.decode(log.getData(), MY_EVENT.getParameters());
				finalTempJson.addProperty("id", eventParam.get(1).getValue().toString());
				finalTempJson.addProperty("@context", eventParam.get(2).getValue().toString());
				finalTempJson.addProperty("identifier", eventParam.get(3).getValue().toString());
				finalTempJson.addProperty("buildingName", eventParam.get(4).getValue().toString());
				finalTempJson.addProperty("location", eventParam.get(5).getValue().toString());
				finalTempJson.addProperty("timestamp", eventParam.get(6).getValue().toString());
				finalTempJson.addProperty("energy", eventParam.get(7).getValue().toString());
			}
		});
		return finalTempJson;
	}

	/**
	 * Method not used actually
	 */
	public static EventValues staticExtractEventParameters(Event event, Log log) {
		final List<String> topics = log.getTopics();
		String encodedEventSignature = EventEncoder.encode(event);
		if (topics == null || topics.size() == 0 || !topics.get(0).equals(encodedEventSignature)) {
			return null;
		}
		List<Type> indexedValues = new ArrayList<>();
		List<Type> nonIndexedValues =
				FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

		List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
		for (int i = 0; i < indexedParameters.size(); i++) {
			Type value =
					FunctionReturnDecoder.decodeIndexedValue(
							topics.get(i + 1), indexedParameters.get(i));
			indexedValues.add(value);
		}
		return new EventValues(indexedValues, nonIndexedValues);
	}

	public static void main(String[]args) {
		Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
		Event MY_EVENT = new Event("MyEvent", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Uint256>(true) {}));

		String MY_EVENT_HASH = EventEncoder.encode(MY_EVENT);


		// Filter
		EthFilter filter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(140)), DefaultBlockParameterName.LATEST, "0xe78A0F7E598Cc8b0Bb87894B0F60dD2a88d6a8Ab");
		//		EthFilter filter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(20)), DefaultBlockParameter.valueOf(BigInteger.valueOf(98)), "0xe78A0F7E598Cc8b0Bb87894B0F60dD2a88d6a8Ab");


		//		System.out.println(">>>>>>>>>>><");
		//		
		//		Log log = new Log();
		//		log.setAddress("");
		//		log.setBlockNumber("");
		//		
		//		System.out.println(">>>>>>>>>>><");
		//		List<Type> eventParam = FunctionReturnDecoder.decode(web3j.ethLogFlowable(filter).toList().blockingGet().get(0).getData(), MY_EVENT.getParameters());
		//		System.out.println(eventParam.get(0).getValue().toString());

		JsonArray finalJson = new JsonArray();
		// Pull all the events for this contract
		web3j.ethLogFlowable(filter).subscribe(log -> {
			String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash
			//			System.out.println(eventHash);
			//			System.out.println(log.getTransactionHash());
			if(eventHash.equals(MY_EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH) 
				JsonObject finalTempJson = new JsonObject();
				List<Type> eventParam = FunctionReturnDecoder.decode(log.getData(), MY_EVENT.getParameters());
				finalTempJson.addProperty("@context", eventParam.get(1).getValue().toString());
				//				System.out.println(eventParam.get(1).getValue().toString());
				finalTempJson.addProperty("identifier", eventParam.get(2).getValue().toString());
				finalTempJson.addProperty("buildingName", eventParam.get(3).getValue().toString());
				finalTempJson.addProperty("location", eventParam.get(4).getValue().toString());
				finalTempJson.addProperty("timestamp", eventParam.get(5).getValue().toString());
				finalTempJson.addProperty("energy", eventParam.get(6).getValue().toString());
				finalJson.add(finalTempJson);
			}
		});

		System.out.println(finalJson);
	}

}
