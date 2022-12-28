package oeg.upm.influxdb;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

import helio.blueprints.TranslationUnit;
import helio.blueprints.UnitBuilder;
import helio.blueprints.components.ComponentType;
import helio.blueprints.components.Components;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import helio.builder.siot.rx.SIoTRxBuilder;

public class IntroduceInfluxDB {

	private static Extract_Energy_Devices_Influx energy = new Extract_Energy_Devices_Influx();
	private static Extract_Temp_Device_Influx temp = new Extract_Temp_Device_Influx();

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
			temp.recoverTempDeviceInflux(initialBlock, endBlock);
		}else {
			energy.recoverEnergyDeviceInflux(initialBlock, endBlock);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		IntroduceInfluxDB inf = new IntroduceInfluxDB();
		long startTime = System.nanoTime();
		inf.configure(0, "*", true);
		long stopTime = System.nanoTime();
		System.out.println(stopTime - startTime);
		System.exit(0);
	}




}
