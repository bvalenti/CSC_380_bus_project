/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.time.LocalTime;

/**
 *
 * @author bill
 */
public class Bus {
    
    public float longitude, lattitude;
    public String id, routeNum, destinationName, origin;
    public LocalTime expectedArrivalTimeEnd, timeOfLastPolling;
    Stop busRoute[];
    
    public Bus(){}
    
}
