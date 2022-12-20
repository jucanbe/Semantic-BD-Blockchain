package oeg.upm.couchdb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IntroduceInDB {

	private static Extract_Energy_Devices_Couch energy = new Extract_Energy_Devices_Couch();
	private static Extract_Temp_Device_Couch temp = new Extract_Temp_Device_Couch();

	private static String dataBase = "devicedb";

	public static void configure(int initial, String end, Boolean isEnergy) {
		DefaultBlockParameter initialBlock;
		DefaultBlockParameter endBlock;
		if(initial == 0) {
			initialBlock = DefaultBlockParameterName.EARLIEST;
		}else {
			initialBlock = DefaultBlockParameter.valueOf(BigInteger.valueOf(initial));
		}
		if(end.contentEquals("*")) {
			endBlock = DefaultBlockParameterName.LATEST;
		}else{
			try{
				if(initial > Integer.parseInt(end)) {
					System.out.println("The initial block must be higher than the final block");
				}
			}catch(Exception e) {
				System.out.println("The final block must be * or a number");
			}
			endBlock = DefaultBlockParameter.valueOf(BigInteger.valueOf(Integer.parseInt(end)));
		}
		if(!isEnergy) {
			temp.recoverTempDevice(initialBlock, endBlock);
		}else {
			energy.recoverEnergyDevice(initialBlock, endBlock);
		}
	}

	public void store(String JsonObject, String hash) throws MalformedURLException, InterruptedException {
		JsonElement element = JsonParser.parseString(JsonObject);
		JsonObject jsonObject = element.getAsJsonObject();
		//			System.out.println(jsonArray.get(i).getAsString());
		byte[] germanBytes = jsonObject.toString().getBytes();
		JsonObject = new String(germanBytes,StandardCharsets.UTF_8);
		InputStream jsonInputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
		StdHttpClient.Builder builder = new StdHttpClient.Builder();
		builder.username("admin");
		builder.password("admin");
		HttpClient httpClient = builder.url("http://localhost:5984").build();  
		CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);  
		CouchDbConnector db = new StdCouchDbConnector(dataBase, dbInstance);
		db.update(hash,
				jsonInputStream,
				jsonObject.toString().length(),
				null);
	}

	public static void main( String[] args ) throws MalformedURLException{
		IntroduceInDB iiDB = new IntroduceInDB();
		iiDB.configure(0, "*", false);
		System.exit(0);
	}

}
