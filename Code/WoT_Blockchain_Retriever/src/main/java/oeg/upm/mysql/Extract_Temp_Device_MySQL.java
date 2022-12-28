package oeg.upm.mysql;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import com.google.gson.JsonObject;

import oeg.upm.WoT_Blockchain_Retriever.Tokens;

public class Extract_Temp_Device_MySQL {
		
	public void recoverTempDevice(DefaultBlockParameter firstBlock, DefaultBlockParameter finalBlock) {
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
				JsonObject finalTempJson = new JsonObject();
				List<Type> eventParam = FunctionReturnDecoder.decode(log.getData(), MY_EVENT.getParameters());
				String context = eventParam.get(1).getValue().toString();
				String id = eventParam.get(2).getValue().toString();
				String build = eventParam.get(3).getValue().toString();
				String location = eventParam.get(4).getValue().toString();
				String office = eventParam.get(5).getValue().toString();
				String timestamp = eventParam.get(6).getValue().toString();
				String lux = eventParam.get(7).getValue().toString();
				String co2 = eventParam.get(8).getValue().toString();
				String humidity = eventParam.get(9).getValue().toString();
				String temp = eventParam.get(10).getValue().toString();

				storeDevice(context, id, build, location, office, timestamp, lux, co2, humidity, temp);
			}
		});
	}
	
	public void storeDevice(String context, String identifier, String building,String location, String office, 
			String timestamp, String lux, String co2, String humidity, String temp) throws MalformedURLException, InterruptedException {
		Connection conn = null;
		try {
			Timestamp tsConverted = new Timestamp(Long.parseLong(timestamp));
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/energy?user=root&password=Lotus123_");
		    String consulta = "INSERT INTO `devices`.`devicesdata` "
		    		+ "(context, identifier, building, location, office, timestamp, lux, co2, humidity, temp) VALUES "
		    		+ "('"+context+"','"+identifier+"','"+building+"','"+location+"','"+office+"','"+tsConverted+"','"
		    		+ Double.parseDouble(lux)/10000+"','"+Double.parseDouble(co2)/10000+"','"+Double.parseDouble(humidity)/10000+"','"
		    		+Double.parseDouble(temp)/10000+"');";
//		    System.out.println(consulta);
		    Statement sentencia = conn.createStatement();
		    sentencia.executeUpdate(consulta);
		    conn.close();
		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

}
