package oeg.upm.agent;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import oeg.upm.iot.General_Sim;
import oeg.upm.management.ContractManagement;
import oeg.upm.management.FileManagement;
import oeg.upm.multiagent.AgentBase;
import oeg.upm.multiagent.AgentModel;

public class Sensor_agent extends AgentBase{

	private static final long serialVersionUID = 1L;

	public static final String NICKNAME = "Sensor";

	private int building;
	private int office;
	private DFAgentDescription[] coordinator_agent = new DFAgentDescription[1];
	private General_Sim gs = new General_Sim();
	private String ethPublicAddr;
	private String ethPrivAddr;
	private ContractManagement sensorContract = new ContractManagement();

	protected void setup(){
		super.setup();
		Object buildingO = getArguments()[0];
		Object officeO = getArguments()[1];
		building = Integer.parseInt(buildingO.toString());
		office = Integer.parseInt(officeO.toString());
		System.out.println("Created SENSOR agent in building " + building + " and office " + office);
		this.type = AgentModel.SENSOR;
		setKeys();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		addBehaviour(new Sensor());
		registerAgentDF();
	}

	private void setKeys() {
		String reader = FileManagement.fileToString("./json/address.json");
		JsonObject jo = JsonParser.parseString(reader).getAsJsonObject();
		JsonArray ja = jo.get("address").getAsJsonArray();
		for(int i = 0; i<ja.size();i++) {
			String a = Integer.toString(building) + Integer.toString(office);
			if(ja.get(i).getAsJsonObject().get("building").getAsInt()==Integer.parseInt(a)) {
				ethPublicAddr = ja.get(i).getAsJsonObject().get("publicKey").getAsString();
				ethPrivAddr = ja.get(i).getAsJsonObject().get("privateKey").getAsString();
			}
		}
	}

	private class Sensor extends CyclicBehaviour{

		public void reset() {
			super.reset();
			System.out.println("Agent reset");
		}

		@Override
		public void action() {
			ACLMessage message = blockingReceive();
			coordinator_agent = getAgentsDF(AgentModel.COORDINATOR);
			if(message.getSender().getName().contentEquals(coordinator_agent[0].getName().getName())) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//DO SOME STUFF
				if(message.getContent()!=null) {
					long timestamp = Long.parseLong(message.getContent());
					Double light = gs.device_Retriever(building, office, "light")*100;
					Double co2 = gs.device_Retriever(building, office, "co2")*100;
					Double humidity = gs.device_Retriever(building, office, "humidity")*100;
					Double temperature = gs.device_Retriever(building, office, "temperature")*100;
					String city;
					switch (building) {
					case 1:
						city = "Madrid";
						break;
					case 2:
						city = "Paris";
						break;
					case 3:
						city = "Londres";
						break;
					default:
						city = "unknown";
						break;
					}
					sensorContract.introduceGeneralValues(ethPrivAddr, "building"+building, city, "office"+office, timestamp, light.intValue(), co2.intValue(), humidity.intValue(), temperature.intValue());
				}			

				ACLMessage finish = new ACLMessage(ACLMessage.INFORM);
				finish.setSender(getAID());
				AID id = new AID(coordinator_agent[0].getName().getName(), AID.ISGUID);
				finish.addReceiver(id);
				send(finish);
			}else {
				System.out.println("Mensaje origen desconocido");
			}
		}

	}

}
