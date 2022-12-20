package oeg.upm.WoT_Blockchain_Retriever;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import oeg.upm.extractor.Extract_Energy_Devices;
import oeg.upm.extractor.Extract_Temp_Device;

public class Main{

	private Extract_Temp_Device temp = new Extract_Temp_Device();
	private Extract_Energy_Devices energy = new Extract_Energy_Devices();

	//InfluxDB
    private static char[] token = Tokens.TOKENINFDB.toCharArray();
    private static String org = "8f859ed7094ddbe4";
    private static String bucket = "MBD";

	public String configure(int initial, String end, Boolean isEnergy) {
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
			return temp.recoverTempDevice(initialBlock, endBlock);
		}else {
			return energy.recoverEnergyDevice(initialBlock, endBlock);
		}
	}
	
	public void store (String jsonData) {
		InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        
        JsonElement element = JsonParser.parseString(jsonData);
        JsonArray jsonArray = element.getAsJsonArray();
        
        for(int i = 0; i < jsonArray.size();i++) {
            Point point = Point.measurement("temperature")
                    .addTag("location", "west")
                    .addField("value", 55D)
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);

            writeApi.writePoint(point);
            writeApi.writeRecord(WritePrecision.NS, "temperature,location=north value=60.0");	
        }
        
        Point point = Point.measurement("temperature")
                .addTag("location", "west")
                .addField("value", 55D)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);

        writeApi.writePoint(point);
        writeApi.writeRecord(WritePrecision.NS, "temperature,location=north value=60.0");

        Temperature temperature = new Temperature();
        temperature.location = "south";
        temperature.value = 62D;
        temperature.time = Instant.now();

        writeApi.writeMeasurement( WritePrecision.NS, temperature);
        
        String flux = "from(bucket:\""+ bucket +"\") |> range(start: 0)";

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }
        influxDBClient.close();
	}
	
    @Measurement(name = "temperature")
    private static class Temperature {

        @Column(tag = true)
        String location;

        @Column
        Double value;

        @Column(timestamp = true)
        Instant time;
    }

	public static void main(String[] args) {
		Main a = new Main();
		a.configure(8000, "9000", false);
//		a.store(null);
		System.exit(0);
	}

}
