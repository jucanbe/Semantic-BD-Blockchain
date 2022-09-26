package oeg.upm.multiagent;

public enum AgentModel {

	SENSOR("Sensor"),
	ENERGY("Energy"),
	COORDINATOR("Coordinator"),
	UNKNOWN("Unknown");

    private final String value;

    AgentModel(String value){ 
    	this.value = value; 
    }

    public String getValue(){ 
    	return this.value; 
    }

    public static AgentModel getEnum(String value) {
        switch (value) {
            case "Sensor":
                return SENSOR;
            case "Energy":
                return ENERGY;
            case "Coordinator":
            	return COORDINATOR;
            default:
                return UNKNOWN;
        }
    }
}
