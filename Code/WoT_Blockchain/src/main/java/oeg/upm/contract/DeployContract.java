package oeg.upm.contract;

import org.web3j.codegen.TruffleJsonFunctionWrapperGenerator;

public class DeployContract {
	
	public static void main(String[] args) throws Exception {
        String outputDir = "/Users/jcano/Documents/Universidad/Master Big Data/TFM/JavaCode/WoT_Blockchain/src/main/java";
        String packageName = "oeg.upm.contract";  //output package
        String[] contractGenerationArgs = new String[7];
        contractGenerationArgs[0] = "generate";
        contractGenerationArgs[1] = "--javaTypes";
//        contractGenerationArgs[2] = "/Users/jcano/Documents/Universidad/Master Big Data/TFM/SmartContractDemo/Sensor_IoT/build/contracts/Sensor_IoT.json";
        contractGenerationArgs[2] = "/Users/jcano/Documents/Universidad/Master Big Data/TFM/SmartContractDemo/Energy_IoT/build/contracts/Energy_IoT.json";
        contractGenerationArgs[3] = "-o";
        contractGenerationArgs[4] = outputDir;
        contractGenerationArgs[5] = "-p";
        contractGenerationArgs[6] = packageName;
        TruffleJsonFunctionWrapperGenerator.run(contractGenerationArgs);
	}

}
