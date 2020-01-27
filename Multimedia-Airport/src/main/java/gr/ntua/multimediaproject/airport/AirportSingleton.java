package gr.ntua.multimediaproject.airport;

import gr.ntua.multimediaproject.stations.DockingStation;

import java.util.HashSet;
import java.util.Set;

public class AirportSingleton {
    private static AirportSingleton airport;

    private Set<DockingStation> availableDockingStations;
    private Set<DockingStation> reservedDockingStations;


    public static AirportSingleton getInstance(){
        if(airport == null){
            airport = new AirportSingleton();
        }
        return airport;
    }

    private AirportSingleton(){
        availableDockingStations = new HashSet<>();
        reservedDockingStations = new HashSet<>();
    }

    //TODO add flight file
    public void initializeAirportFromFiles(){

    }

}
