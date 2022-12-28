package oeg.upm.influxdb;

import java.math.BigInteger;
import java.time.Instant;
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
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import oeg.upm.WoT_Blockchain_Retriever.Tokens;

public class Extract_Temp_Device_Influx {
	
	private static char[] token = "-5fb_yYTiKviNOkLaJer9zaU5qdtKAP2Q9c2Cuv9i2LOIq8EShNeIgDsdWVB7dcObi0WpPa6GKLPDICDQBrZSQ==".toCharArray();
	private static String org = "8f859ed7094ddbe4";
	private static String bucket = "devices";
	
	public void recoverTempDeviceInflux(DefaultBlockParameter firstBlock, DefaultBlockParameter finalBlock) {
		Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
		
		Event MY_EVENT = new Event("MyEvent", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Utf8String>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Uint256>(true) {},
				new TypeReference<Int256>(true) {}));
		String MY_EVENT_HASH = EventEncoder.encode(MY_EVENT);

		// Filter
		EthFilter filter = new EthFilter(firstBlock, finalBlock, Tokens.TEMPDIR);

		// Pull all the events for this contract
		web3j.ethLogFlowable(filter).subscribe(log -> {
			String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash
			if(eventHash.equals(MY_EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH) 
				List<Type> eventParam = FunctionReturnDecoder.decode(log.getData(), MY_EVENT.getParameters());
				String buildingName = eventParam.get(3).getValue().toString();
				String location = eventParam.get(4).getValue().toString();
				String office = eventParam.get(5).getValue().toString();
				String timestamp = eventParam.get(6).getValue().toString();
				String lux = eventParam.get(7).getValue().toString();
				String co2 = eventParam.get(8).getValue().toString();
				String humidity = eventParam.get(9).getValue().toString();
				String temp = eventParam.get(10).getValue().toString();
				store(location, buildingName, office, timestamp, lux, co2, humidity, temp);
			}
		});
	}
	
	public void store(String location, String buildingName, String office, String timestamp, String lux, String co2, String humidity, String temp) {
		InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
		
		WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
		Point point = Point.measurement("lux")
				.addTag("location", location)
				.addTag("buildingName", buildingName)
				.addTag("office", office)
				.addField("value", Double.parseDouble(lux)/10000)
				.time(Instant.ofEpochMilli(Long.parseLong(timestamp)), WritePrecision.MS);
		
		writeApi.writePoint(point);
		
		point = Point.measurement("co2")
				.addTag("location", location)
				.addTag("buildingName", buildingName)
				.addTag("office", office)
				.addField("value", Double.parseDouble(co2)/10000)
				.time(Instant.ofEpochMilli(Long.parseLong(timestamp)), WritePrecision.MS);
		
		writeApi.writePoint(point);
		
		point = Point.measurement("humidity")
				.addTag("location", location)
				.addTag("buildingName", buildingName)
				.addTag("office", office)
				.addField("value", Double.parseDouble(humidity)/10000)
				.time(Instant.ofEpochMilli(Long.parseLong(timestamp)), WritePrecision.MS);
		
		writeApi.writePoint(point);
		
		point = Point.measurement("temperature")
				.addTag("location", location)
				.addTag("buildingName", buildingName)
				.addTag("office", office)
				.addField("value", Double.parseDouble(temp)/10000)
				.time(Instant.ofEpochMilli(Long.parseLong(timestamp)), WritePrecision.MS);
		
		writeApi.writePoint(point);

		influxDBClient.close();
		
		
	}

}
