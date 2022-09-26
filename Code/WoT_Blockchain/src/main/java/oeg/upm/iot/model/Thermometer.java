package oeg.upm.iot.model;

public class Thermometer {
	
	public String address;
	public String ontology;	
	public String id;
	public int temp_min;
	public int temp_max;
	public int timestamp;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOntology() {
		return ontology;
	}
	public void setOntology(String ontology) {
		this.ontology = ontology;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(int temp_min) {
		this.temp_min = temp_min;
	}
	public int getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(int temp_max) {
		this.temp_max = temp_max;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

}
