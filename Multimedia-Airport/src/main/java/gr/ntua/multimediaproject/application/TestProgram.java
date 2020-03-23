package gr.ntua.multimediaproject.application;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import gr.ntua.multimediaproject.airport.exceptions.AirportException;

public class TestProgram {
    public static void main(String[] args){
        AirportSingleton airport = AirportSingleton.getInstance();
//        airport.clearAirport();
        try{
            airport.initializeAirportContentFromFiles("default");
        }
        catch(Exception ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            return;
        }
        System.out.println("TEST: " + airport);
        try {
            airport.landHoldingFlightsInFreeLandingSpaces();
        } catch (AirportException ex) {
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
        try {
            Thread.sleep(3000);
//            System.out.println(Thread.getAllStackTraces().keySet());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TEST: " + airport);
        airport.clearAirport();
        try {
            Thread.sleep(2000);
//            System.out.println(Thread.getAllStackTraces().keySet());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TEST: " + airport);
    }
}
