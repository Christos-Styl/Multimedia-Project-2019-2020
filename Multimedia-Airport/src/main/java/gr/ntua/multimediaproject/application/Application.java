package gr.ntua.multimediaproject.application;

import gr.ntua.multimediaproject.airport.AirportSingleton;

public class Application {
    public static void main(String[] args){
        AirportSingleton airport = AirportSingleton.getInstance();
        airport.initializeAirportContentFromFiles();
        System.out.println(airport);
    }
}
