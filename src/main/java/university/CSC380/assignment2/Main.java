package university.CSC380.assignment2;

	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.net.HttpURLConnection;
	import java.net.MalformedURLException;
	import java.net.URL;
	import java.net.URLConnection;
	import java.util.HashMap;
	
	
	public class Main {
	    
	    public static void main(String[] args) throws FileNotFoundException, IOException{
	        
	        //Utility.startupTasks();
	        
	        //HashMap hm = Utility.parseSchedule("Routes.txt");
	        
	        String fileName = Utility.getFile("http://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880", "C:\\Users\\Benjamin\\Desktop");
	    
	        
	    
	    
	    }
	    
	}
	
	

