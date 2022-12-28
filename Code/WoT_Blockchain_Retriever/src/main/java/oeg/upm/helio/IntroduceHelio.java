package oeg.upm.helio;

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
import helio.blueprints.exceptions.IncompatibleMappingException;
import helio.blueprints.exceptions.IncorrectMappingException;
import helio.blueprints.exceptions.TranslationUnitExecutionException;
import helio.builder.siot.rx.SIoTRxBuilder;

public class IntroduceHelio {

	private static Extract_Energy_Devices energy = new Extract_Energy_Devices();
	private static Extract_Temp_Device temp = new Extract_Temp_Device();

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
			temp.recoverTempDevice(initialBlock, endBlock);
		}else {
			energy.recoverEnergyDevice(initialBlock, endBlock);
		}
	}
	

	private static String readMapping(String file) {
		try {
			return Files.readString(Path.of(file));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		try {
			Components.registerAndLoad("https://github.com/helio-ecosystem/helio-provider-files/releases/download/v0.1.1/helio-provider-files-0.1.1.jar", "helio.providers.files.FileProvider", ComponentType.PROVIDER);
			Components.registerAndLoad("https://github.com/helio-ecosystem/helio-handler-jayway/releases/download/v0.1.1/helio-handler-jayway-0.1.1.jar", "handlers.JsonHandler", ComponentType.HANDLER);
			Components.registerAndLoad("https://github.com/helio-ecosystem/helio-providers-web/releases/download/v0.1.1/helio-providers-web-0.1.1.jar", "helio.providers.HttpProvider", ComponentType.PROVIDER);
			String mapping = readMapping("./src/test/resources/sync-tests/01-mapping.ldmap");
			
			UnitBuilder builder = new SIoTRxBuilder();
			Set<TranslationUnit> list = builder.parseMapping(mapping);
			TranslationUnit unit = list.iterator().next();

			  ExecutorService service = Executors.newFixedThreadPool(4);
//			  Future<?> f = service.submit(unit.getTask());
//			  f.get();
			  String result = unit.getId(); // data translated
//			  f.cancel(true);
//			  service.shutdown();
		} catch (ExtensionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncompatibleMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranslationUnitExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncorrectMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IntroduceHelio inf = new IntroduceHelio();
		long startTime = System.nanoTime();
		inf.configure(0, "*", true);
		long stopTime = System.nanoTime();
		System.out.println(stopTime - startTime);
		System.exit(0);
	}



}
