package gr.ntua.multimediaproject.application;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import gr.ntua.multimediaproject.ui.AirportGui;
import javafx.stage.Stage;

public class Program {
    public static void main(String[] args){
        AirportSingleton airport = AirportSingleton.getInstance();
        airport.clearAirport();
//        try{
//            airport.initializeAirportContentFromFiles();
//        }
//        catch(Exception ex){
//            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
//            return;
//        }
//        System.out.println("TEST: " + airport);
//        airport.landHoldingFlightsInFreeLandingSpaces();
//        try {
//            Thread.sleep(5000);
////            System.out.println(Thread.getAllStackTraces().keySet());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("TEST: " + airport);
//        airport.clearAirport();
//        try {
//            Thread.sleep(2000);
////            System.out.println(Thread.getAllStackTraces().keySet());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("TEST: " + airport);
    }
}
