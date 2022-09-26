package oeg.upm.WoT_Blockchain;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.WalletUtils;


public class GanacheInfoToJSON {
	
    public static void main( String[] args ){
    	Web3j web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
		try {
	    	List<String> account = web3.ethAccounts().send().getAccounts();
	    	JsonArray ja = new JsonArray();
	    	JsonObject finalJO = new JsonObject();
	    	int building = 1;
	    	int office = 1;
	    	for(int i = 0; i<account.size();i++) {
		    	JsonObject jo = new JsonObject();
	    		String password = null; // no encryption
	    		String mnemonic = "still trial aspect alpha plate taste gaze street purchase ketchup decorate hotel";
	    		byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
	    		Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
	    		final int[] path = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0, i};
	    		Bip32ECKeyPair childKeypair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, path);
	    		Credentials credential = Credentials.create(childKeypair);
	    		if(i<3) {
		    		jo.addProperty("building", i+1);
	    		}else {
	    			String a = Integer.toString(building) + Integer.toString(office);
		    		jo.addProperty("building", Integer.parseInt(a));
		    		office++;
		    		if(office > 15) {
		    			office = 1;
		    			building++;
		    		}
	    		}
	    		jo.addProperty("publicKey", credential.getAddress().toUpperCase());
	    		jo.addProperty("privateKey", credential.getEcKeyPair().getPrivateKey().toString(16));
	    		ja.add(jo);
	    	}
	    	finalJO.add("address", ja);
	    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	String jsonOutput = gson.toJson(finalJO);
	    	FileUtils.writeStringToFile(new File("./json/address.json"), jsonOutput, Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
