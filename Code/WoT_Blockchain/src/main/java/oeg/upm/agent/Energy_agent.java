package oeg.upm.agent;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import oeg.upm.iot.General_Sim;
import oeg.upm.management.ContractManagement;
import oeg.upm.management.FileManagement;
import oeg.upm.multiagent.AgentBase;
import oeg.upm.multiagent.AgentModel;

public class Energy_agent extends AgentBase{

	private static final long serialVersionUID = 1L;

	public static final String NICKNAME = "Energy";

	private int building;
	private DFAgentDescription[] coordinator_agent = new DFAgentDescription[1];
	private General_Sim gs = new General_Sim();
	private ContractManagement energyContract = new ContractManagement();
	private String ethPublicAddr;
	private String ethPrivAddr;

	protected void setup(){
		super.setup();
		Object buildingO = getArguments()[0];
		building = Integer.parseInt(buildingO.toString());
		System.out.println("Created ENERGY agent in building " + building);
		this.type = AgentModel.ENERGY;
		setKeys();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		addBehaviour(new Energy());
		registerAgentDF();
	}

	private void setKeys() {
		String reader = FileManagement.fileToString("./json/address.json");
		JsonObject jo = JsonParser.parseString(reader).getAsJsonObject();
		JsonArray ja = jo.get("address").getAsJsonArray();
		for(int i = 0; i<ja.size();i++) {
			if(ja.get(i).getAsJsonObject().get("building").getAsInt()==building) {
				ethPublicAddr = ja.get(i).getAsJsonObject().get("publicKey").getAsString();
				ethPrivAddr = ja.get(i).getAsJsonObject().get("privateKey").getAsString();
			}
		}
	}

	private class Energy extends CyclicBehaviour{

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

				if(message.getContent()!=null) {
					long timestamp = Long.parseLong(message.getContent());
					Double energy = gs.device_Retriever(building)/100;
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
					energyContract.introduceEnergyValues(ethPrivAddr, "building"+building,city,timestamp,energy.intValue());

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
