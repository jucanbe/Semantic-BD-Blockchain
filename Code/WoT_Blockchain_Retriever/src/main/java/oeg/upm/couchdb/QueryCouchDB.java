package oeg.upm.couchdb;

import java.net.MalformedURLException;
import java.util.List;

import org.lightcouch.CouchDbClient;

import com.google.gson.JsonObject;

public class QueryCouchDB {
	
	public static void main( String[] args ) throws MalformedURLException{
		CouchDbClient dbClient = new CouchDbClient(); 

		long startTime = System.nanoTime();
		List<JsonObject> allDocs = dbClient.view("_all_docs").includeDocs(true).query(JsonObject.class);
		long stopTime = System.nanoTime();
		System.out.println(stopTime - startTime);
		System.exit(0);
	}

}
