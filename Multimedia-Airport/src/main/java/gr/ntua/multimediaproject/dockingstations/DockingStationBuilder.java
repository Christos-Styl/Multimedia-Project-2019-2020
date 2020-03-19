package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.offeredservices.*;
import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DockingStationBuilder {
    private DockingStation dockingStation;

    public static DockingStationBuilder create(){
        return new DockingStationBuilder();
    }

    public DockingStationBuilder ofDockingStationType(DockingStationType type) throws DockingStationException{
        Set<OfferedService> offeredServicesSet = new HashSet<>();
        switch(type){
            case GATE:
                dockingStation = new GateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(45);
                offeredServicesSet.addAll(Arrays.asList(
                        RefuelingOfferedServiceSingleton.getInstance(),
                        CleaningOfferedServiceSingleton.getInstance(),
                        PassengerTransitOfferedServiceSingleton.getInstance(),
                        LoadingUnloadingOfferedServiceSingleton.getInstance()));
                break;
            case COMMERCIAL_GATE:
                dockingStation = new CommercialGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(90);
                offeredServicesSet.addAll(Arrays.asList(
                        RefuelingOfferedServiceSingleton.getInstance(),
                        CleaningOfferedServiceSingleton.getInstance(),
                        PassengerTransitOfferedServiceSingleton.getInstance(),
                        LoadingUnloadingOfferedServiceSingleton.getInstance()));
                break;
            case ZONE_A:
                dockingStation = new ZoneADockingStation();
                dockingStation.setMaxLandingDurationInMinutes(90);
                offeredServicesSet.addAll(Arrays.asList(
                        RefuelingOfferedServiceSingleton.getInstance(),
                        CleaningOfferedServiceSingleton.getInstance(),
                        PassengerTransitOfferedServiceSingleton.getInstance(),
                        LoadingUnloadingOfferedServiceSingleton.getInstance()));
                break;
            case ZONE_B:
                dockingStation = new ZoneBDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(120);
                offeredServicesSet.addAll(Arrays.asList(
                        RefuelingOfferedServiceSingleton.getInstance(),
                        CleaningOfferedServiceSingleton.getInstance(),
                        PassengerTransitOfferedServiceSingleton.getInstance(),
                        LoadingUnloadingOfferedServiceSingleton.getInstance()));
                break;
            case ZONE_C:
                dockingStation = new ZoneCDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(180);
                offeredServicesSet.addAll(Arrays.asList(
                        RefuelingOfferedServiceSingleton.getInstance(),
                        CleaningOfferedServiceSingleton.getInstance(),
                        PassengerTransitOfferedServiceSingleton.getInstance(),
                        LoadingUnloadingOfferedServiceSingleton.getInstance()));
                break;
            case GENERAL_DOCKING_STATION:
                dockingStation = new GeneralDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(240);
                offeredServicesSet.addAll(Arrays.asList(
                        RefuelingOfferedServiceSingleton.getInstance(),
                        CleaningOfferedServiceSingleton.getInstance()));
                break;
            case LONG_DURATION:
                dockingStation = new LongDurationDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(600);
                offeredServicesSet.addAll(Arrays.asList(
                        RefuelingOfferedServiceSingleton.getInstance(),
                        CleaningOfferedServiceSingleton.getInstance(),
                        PassengerTransitOfferedServiceSingleton.getInstance(),
                        LoadingUnloadingOfferedServiceSingleton.getInstance()));
                break;
            default:
                throw new DockingStationException("The Docking Station Type specified does not exist.");
        }
        dockingStation.setOfferedServiceSet(offeredServicesSet);
        return this;
    }

    public DockingStationBuilder ofIdPrefixAndNumberOfSpaces(String namePrefix, int numberOfSpaces)
            throws DockingStationException{
        if(numberOfSpaces <= 0){
            throw new DockingStationException("Docking Station should have a positive number of docking spaces.");
        }
        List<DockingSpace> dockingSpaceList = new CopyOnWriteArrayList<>();
        for(int count=0; count < numberOfSpaces; count++){
            DockingSpace dockingSpace = new DockingSpace();
            dockingSpace.setFlight(null);
            dockingSpace.setId(namePrefix + count);
            dockingSpace.setDockingStation(dockingStation);
            dockingSpaceList.add(dockingSpace);
        }
        dockingStation.setDockingSpaceList(dockingSpaceList);
        dockingStation.setNumberOfFreeDockingSpaces(numberOfSpaces);
        return this;
    }

    public DockingStationBuilder ofCost(double cost) throws DockingStationException{
        if(cost <= 0){
            throw new DockingStationException("Cost must be a positive double.");
        }
        dockingStation.setCost(cost);
        return this;
    }

    public DockingStation build() throws DockingStationException{
        if(!doesDockingStationHaveRequiredFields()){
            throw new DockingStationException("Flight does not have all required fields.");
        }
        return dockingStation;
    }

    public boolean doesDockingStationHaveRequiredFields(){
        return (dockingStation.getNumberOfFreeDockingSpaces() != 0 &&
                dockingStation.getDockingSpaceList() != null &&
                dockingStation.getCost() != 0 &&
                dockingStation.getMaxLandingDurationInMinutes() != 0 &&
                dockingStation.getOfferedServiceSet() != null);
    }

}
