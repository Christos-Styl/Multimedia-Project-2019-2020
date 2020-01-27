package gr.ntua.multimediaproject.application;

import gr.ntua.multimediaproject.airport.AirportSingleton;

public class Application {
    public static void main(String[] args){
        AirportSingleton airport = AirportSingleton.getInstance();
        try{
            airport.initializeAirportContentFromFiles();
        }
        catch(Exception ex){
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
            return;
        }
        System.out.println(airport);
    }
}
