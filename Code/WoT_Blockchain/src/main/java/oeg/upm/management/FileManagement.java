package oeg.upm.management;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FileManagement {
	
	public static void removeFirstLine(String fileName) throws IOException {  
	    RandomAccessFile raf = new RandomAccessFile(fileName, "rw");          
	    long writePosition = raf.getFilePointer();                            
	    raf.readLine();                                                       
	    long readPosition = raf.getFilePointer();                             
	    byte[] buff = new byte[1024];                                         
	    int n;                                                                
	    while (-1 != (n = raf.read(buff))) {                                  
	        raf.seek(writePosition);                                          
	        raf.write(buff, 0, n);                                            
	        readPosition += n;                                                
	        writePosition += n;                                               
	        raf.seek(readPosition);                                           
	    }                                                                     
	    raf.setLength(writePosition);                                         
	    raf.close();                                                          
	} 

	public static String fileToString(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)){
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		}catch (IOException e){
			e.printStackTrace();
		}
		return contentBuilder.toString();    
	}
	
}
