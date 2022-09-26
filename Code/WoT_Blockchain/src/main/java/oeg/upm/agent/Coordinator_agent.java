package oeg.upm.agent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import oeg.upm.management.ContractManagement;
import oeg.upm.management.TimeManagement;
import oeg.upm.multiagent.AgentBase;
import oeg.upm.multiagent.AgentModel;

public class Coordinator_agent extends AgentBase{

	private static final long serialVersionUID = 1L;

	public static final String NICKNAME = "Coordinator";
	
	private TimeManagement tm = new TimeManagement();

	private DFAgentDescription[] general_sensors = new DFAgentDescription[1];
	private DFAgentDescription[] energy_sensor = new DFAgentDescription[1];
	
	private int totalDevices;
	private int missingMessages;
	private File blockDates = new File("./json_dates/blockDates.json");
	private ContractManagement contractManage = new ContractManagement();
	
	protected void setup(){
		super.setup();
		System.out.println("Created COORDINATOR agent");
		this.type = AgentModel.COORDINATOR;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		addBehaviour(new Coordinator());
		registerAgentDF();

		general_sensors = getAgentsDF(AgentModel.SENSOR);
		energy_sensor = getAgentsDF(AgentModel.ENERGY);
		totalDevices = general_sensors.length+energy_sensor.length;
		missingMessages=totalDevices;
		try {
	    	FileUtils.writeStringToFile(blockDates, "block,timestamp\n", Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		SendMessagesBroadcast(general_sensors);
		SendMessagesBroadcast(energy_sensor);
	}
	
	private void SendMessagesBroadcast(DFAgentDescription[] agents) {
		for(int i=0;i<agents.length;i++) {
			ACLMessage finish = new ACLMessage(ACLMessage.INFORM);
			finish.setSender(getAID());
			AID id = new AID(agents[i].getName().getName(), AID.ISGUID);
			finish.addReceiver(id);
			send(finish);
		}
	}

	private class Coordinator extends CyclicBehaviour{

		public void reset() {
			super.reset();
			System.out.println("Agent reset");
		}

		@Override
		public void action() {
			ACLMessage message = blockingReceive();
			//Clonar matriz
			//Borrar de la matriz cuando se reciva un mensaje y cuando llegue a 0 mandar un send()
			if(missingMessages!=0) {
				missingMessages--;
			}
			if(missingMessages==0) {
				missingMessages=totalDevices;	
				System.out.println("Introducir valores");
		    	try {
					FileUtils.writeStringToFile(blockDates, contractManage.getCurrentBlock()+","+Long.toString(tm.timestamp())+"\n", true);
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	
		    	for(int i=0;i<general_sensors.length;i++) {
					ACLMessage finish = new ACLMessage(ACLMessage.INFORM);
					finish.setSender(getAID());
					AID id = new AID(general_sensors[i].getName().getName(), AID.ISGUID);
					finish.addReceiver(id);
					finish.setContent(Long.toString(tm.timestamp()));
					send(finish);
				}
		    	
				for(int i=0;i<energy_sensor.length;i++) {
					ACLMessage finish = new ACLMessage(ACLMessage.INFORM);
					finish.setSender(getAID());
					AID id = new AID(energy_sensor[i].getName().getName(), AID.ISGUID);
					finish.addReceiver(id);
					finish.setContent(Long.toString(tm.timestamp()));
					send(finish);
				}
				tm.addTime();
			}
		}

	}
}
