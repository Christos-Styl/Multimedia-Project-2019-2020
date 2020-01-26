package gr.ntua.multimedia.airport.stations;

import gr.ntua.multimedia.airport.stations.exceptions.DockingStationException;
import gr.ntua.multimedia.airport.offeredservices.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

enum DockingStationType{
    GATE,
    COMMERCIAL_GATE,
    ZONE_A,
    ZONE_B,
    ZONE_C,
    GENERAL_DOCKING_STATION,
    LONG_DURATION
}

public class DockingStationBuilder {
    private DockingStation dockingStation;


    public static DockingStationBuilder create(){
        return new DockingStationBuilder();
    }


    public DockingStationBuilder dockingStationType(DockingStationType type) throws DockingStationException{
        Set<OfferedService> offeredServicesSet = new HashSet<OfferedService>();
        switch(type){
            case GATE:
                dockingStation = new GateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(45);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                break;
            case COMMERCIAL_GATE:
                dockingStation = new CommercialGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(90);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                break;
            case ZONE_A:
                dockingStation = new ZoneAGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(90);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                break;
            case ZONE_B:
                dockingStation = new ZoneBGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(120);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                break;
            case ZONE_C:
                dockingStation = new ZoneCGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(180);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                break;
            case GENERAL_DOCKING_STATION:
                dockingStation = new GeneralDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(240);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService()));
                break;
            case LONG_DURATION:
                dockingStation = new LongDurationDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(600);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                break;
            default: throw new DockingStationException("The Docking Station Type specified does not exist.");
        }
        dockingStation.setStattionState(DockingStationState.EMPTY);
        dockingStation.setOfferedServices(offeredServicesSet);
        return this;
    }


    public DockingStationBuilder idPrefixAndNumber(String namePrefix, int idNumber){
        dockingStation.setId(namePrefix + idNumber);
        return this;
    }


    public DockingStationBuilder ofCost(double cost){
        dockingStation.setCost(cost);
        return this;
    }


    public DockingStation build(){
        return dockingStation;
    }

}
