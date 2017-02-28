/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bill
 */
public class UpdateModel extends TimerTask{
    
    public void run(){
        System.out.println("Executing TimerTask UpdateModel");
        try {
            Utility.getFile("https://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880", "/home/bill/SchoolWork/csc380/CSC_380_bus_project", "vehicle-monitoring.json");
        } catch (IOException ex) {
            System.out.println("IO Exception getting Vehicle Data.");
        }
    }
    
}
