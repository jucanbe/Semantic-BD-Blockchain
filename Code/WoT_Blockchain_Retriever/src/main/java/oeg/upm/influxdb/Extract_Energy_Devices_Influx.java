package oeg.upm.influxdb;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;

import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import oeg.upm.WoT_Blockchain_Retriever.Tokens;

public class Extract_Energy_Devices_Influx {

	private static char[] token = "s5C4oyMgud6R8LkYJEAS1RBoeeXA9F05A4PIgrG86m6bhxrZFso9zrf-ARZ2u3wIEW46FFNK8pzVkb3Nb7HR7A==".toCharArray();
	private static String org = "8f859ed7094ddbe4";
	private static String bucket = "energy";
	
	public void recoverEnergyDeviceInflux(DefaultBlockParameter firstBlock, DefaultBlockParameter finalBlock) {
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
		EthFilter filter = new EthFilter(firstBlock, finalBlock, Tokens.ENERGYDIR);
		
		// Pull all the events for this contract
		web3j.ethLogFlowable(filter).subscribe(log -> {
			String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash
			if(eventHash.equals(MY_EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH) 
				List<Type> eventParam = FunctionReturnDecoder.decode(log.getData(), MY_EVENT.getParameters());
//				finalTempJson.addProperty("@context", eventParam.get(1).getValue().toString());
//				finalTempJson.addProperty("identifier", eventParam.get(2).getValue().toString());
				String buildingName = eventParam.get(3).getValue().toString();
				String location = eventParam.get(4).getValue().toString();
				String timestamp = eventParam.get(5).getValue().toString();
				String energy = eventParam.get(6).getValue().toString();
				store(location, buildingName, energy, timestamp);
			}
		});
	}
	
	public void store(String location, String buildingName, String energy, String timestamp) {
		InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
		
		WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
		Point point = Point.measurement("energy")
				.addTag("location", location)
				.addTag("buildingName", buildingName)
				.addField("value", Double.parseDouble(energy)/10000)
				.time(Instant.ofEpochMilli(Long.parseLong(timestamp)), WritePrecision.MS);
		
		writeApi.writePoint(point);

		influxDBClient.close();
	}


}
