package oeg.upm.multiagent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import oeg.upm.agent.Coordinator_agent;
import oeg.upm.agent.Energy_agent;
import oeg.upm.agent.Sensor_agent;

import java.io.IOException;

public class Main {
	
	private static jade.wrapper.AgentContainer cc;
	
    private static void loadBoot(){

        jade.core.Runtime rt = jade.core.Runtime.instance();

        rt.setCloseVM(true);
        System.out.println("Runtime created");

        Profile profile = new ProfileImpl(null, 1200, null);
        System.out.println("Profile created");

        System.out.println("Launching a whole in-process platform..."+profile);
        cc = rt.createMainContainer(profile);

        try {
            ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
            rt.createAgentContainer(pContainer);
            System.out.println("Containers created");
            System.out.println("Launching the rma agent on the main container ...");
            cc.createNewAgent("rma","jade.tools.rma.rma", new Object[0]).start();
            for(int i=1; i<4;i++) {
            	for(int j=1; j<16;j++) {
            		cc.createNewAgent(Sensor_agent.NICKNAME+i+j, Sensor_agent.class.getName(), new Object[]{i,j}).start();
            	}
            	cc.createNewAgent(Energy_agent.NICKNAME+i, Energy_agent.class.getName(), new Object[]{i}).start();
            }
            try {
    			Thread.sleep(10000);
            	cc.createNewAgent(Coordinator_agent.NICKNAME, Coordinator_agent.class.getName(), new Object[]{0}).start();
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
        } catch (StaleProxyException e) {
            System.err.println("Error during boot!!!");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void main(String[] args) throws IOException {

        System.out.println("Starting...");

        loadBoot();

        System.out.println("MAS loaded...");
    }

}
