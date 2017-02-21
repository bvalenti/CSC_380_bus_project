package university.CSC380.assignment2;

import java.io.*;
import java.net.*;



public class App 
{
    public static void main( String[] args )
    {
    	
    	
    	try {
    	    URL myURL = new URL("http://bustime.mta.info/api/siri/vehicle-monitoring.json");
    	    URLConnection myURLConnection = myURL.openConnection();
    	    myURLConnection.connect();
    	    if (myURLConnection instanceof URLConnection) {
    	    	System.out.println("Newbie Poo");
    	    }
    	    
    	} 
    	catch (MalformedURLException e) { 
    		System.out.println("new URL failed");
    	    // new URL() failed
    	    // ...
    	} 
    	catch (IOException e) {   
    		System.out.println("open connection failed");
    	    // openConnection() failed
    	    // ...
    	}
    	
    	
    	
    	//String url; 
    	
    	
    	
    	
    	
    	
    }
}
