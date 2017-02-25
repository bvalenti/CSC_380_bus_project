package university.CSC380.assignment2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.json.*; 
//import javax.json.JsonObject;
//import javax.json.JsonReader;


public final class Utility {
	
	
	public static void parseJSONFile (HashMap<String, Bus[]> map, String fileName) throws FileNotFoundException {
		
		InputStream fis = new FileInputStream(fileName);
		JsonReader jsonReader = Json.createReader(fis);
		JsonObject jsonObject = jsonReader.readObject();
		
		
		
		
		//jsonObject.get();

	}

	public static void startupTasks() {
		// TODO Auto-generated method stub
		
	}

	public static HashMap parseSchedule(String string) {
		// TODO Auto-generated method stub
		return null;
	}

		
	public static String getFile(String url, String saveLocation) throws MalformedURLException, IOException{
	     
		URL urlToGet = new URL(url);
	        HttpURLConnection httpConn = (HttpURLConnection) 
	                urlToGet.openConnection();
	        int responseCode = httpConn.getResponseCode();
	        String fileName = "";
	        if (responseCode == HttpURLConnection.HTTP_OK){
	            // next line will break if MTA Bus API url with key not used
	            fileName = url.substring(url.lastIndexOf("/") + 1,
	                        url.lastIndexOf("?"));
	            InputStream inputStream = httpConn.getInputStream();
	            String saveFilePath = saveLocation + File.separator + fileName;
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	            int bytesRead = -1;
	            byte[] buffer = new byte[1024];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	            outputStream.close();
	            inputStream.close();
	        }
	        httpConn.disconnect();
	        
	        return fileName;
	    }
	}
