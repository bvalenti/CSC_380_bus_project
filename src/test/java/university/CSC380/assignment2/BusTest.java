/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author bill
 */
public class BusTest extends TestCase{
    
    public BusTest(String testName){
        super(testName);
    }
    
    public static Test suite()
    {
        return new TestSuite( BusTest.class );
    }
    
    @test
    public void testGPSFeed(){
        assertTrue( true );
    }
    
}
