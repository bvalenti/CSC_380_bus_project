package university.CSC380.assignment2;

import java.io.*;
import java.net.*;

public class ConnectURL {
	public static void main( String[] args ) {
    	
    	try {
    	    URL myURL = new URL("http://bustime.mta.info/api/siri/vehicle-monitoring.json");
    	    HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
    	    //URLConnection conn = myURL.openConnection();
    	    conn.connect();
    	    
    	    if (conn instanceof URLConnection) {
    	    	//System.out.println("Newbie Poo");
    	    }
    	    
    	    StringBuilder result = new StringBuilder();
    	    String response = conn.getRequestMethod();
    	    
    	    //8a6b3b92-7c91-4918-b4f9-aeae578492d9 API
    	    
    	    //conn.setRequestMethod("GET");
    	    
    	    /*BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	    String line;
    	      while ((line = rd.readLine()) != null) {
    	         result.append(line);
    	      }
    	      rd.close(); */
    	      
    	      System.out.println(response);
    	      
    	      
    	} catch (MalformedURLException e) { 
    		System.out.println("new URL failed");
    		
    	} catch (IOException e) {   
    		System.out.println("open connection failed");
    	}
    	
    }
	
}
