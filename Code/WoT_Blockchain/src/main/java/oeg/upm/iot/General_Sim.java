package oeg.upm.iot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import oeg.upm.management.FileManagement;

public class General_Sim {

	
	public Double device_Retriever(int building, int office, String type) {
		Reader device;
		try {
			String fileDir = "./csv/building"+ building +"/office"+ office +"/"+type+".csv";
			device = new FileReader(fileDir);
			Iterable<CSVRecord> deviceRecords = CSVFormat.DEFAULT.parse(device);
			Iterator<CSVRecord> deviceRecord = deviceRecords.iterator();
			FileManagement.removeFirstLine(fileDir);
			return Double.valueOf(deviceRecord.next().get(1))*100;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return 0.0;
	}
	
	public Double device_Retriever(int building) {
		Reader device;
		try {
			String fileDir = "./csv/building"+ building +"/building"+building+".csv";
			device = new FileReader(fileDir);
			Iterable<CSVRecord> deviceRecords = CSVFormat.DEFAULT.parse(device);
			Iterator<CSVRecord> deviceRecord = deviceRecords.iterator();
			FileManagement.removeFirstLine(fileDir);
			return Double.valueOf(deviceRecord.next().get(1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return 0.0;
	}

}
