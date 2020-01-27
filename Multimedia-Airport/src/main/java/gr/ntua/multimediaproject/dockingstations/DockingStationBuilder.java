package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.flights.FlightType;
import gr.ntua.multimediaproject.flights.PlaneType;
import gr.ntua.multimediaproject.offeredservices.*;
import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;

import java.util.*;

public class DockingStationBuilder {
    private DockingStation dockingStation;

    public static DockingStationBuilder create(){
        return new DockingStationBuilder();
    }

    public DockingStationBuilder ofDockingStationType(DockingStationType type) throws DockingStationException{
        Set<OfferedService> offeredServicesSet = new HashSet<>();
        Set<PlaneType> supportedPlaneTypes = new HashSet<>();
        Set<FlightType> supportedFlightTypes = new HashSet<>();
        switch(type){
            case GATE:
                dockingStation = new GateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(45);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                supportedPlaneTypes.addAll(Arrays.asList(
                        PlaneType.JET,
                        PlaneType.TURBOPROP
                ));
                supportedFlightTypes.addAll(Arrays.asList(
                        FlightType.PASSENGER
                ));
                break;
            case COMMERCIAL_GATE:
                dockingStation = new CommercialGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(90);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                supportedPlaneTypes.addAll(Arrays.asList(
                        PlaneType.JET,
                        PlaneType.TURBOPROP
                ));
                supportedFlightTypes.addAll(Arrays.asList(
                        FlightType.COMMERCIAL
                ));
                break;
            case ZONE_A:
                dockingStation = new ZoneAGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(90);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                supportedPlaneTypes.addAll(Arrays.asList(
                        PlaneType.JET,
                        PlaneType.TURBOPROP
                ));
                supportedFlightTypes.addAll(Arrays.asList(
                        FlightType.PASSENGER
                ));
                break;
            case ZONE_B:
                dockingStation = new ZoneBGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(120);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                supportedPlaneTypes.addAll(Arrays.asList(
                        PlaneType.JET,
                        PlaneType.TURBOPROP
                ));
                supportedFlightTypes.addAll(Arrays.asList(
                        FlightType.PASSENGER,
                        FlightType.COMMERCIAL,
                        FlightType.PRIVATE
                ));
                break;
            case ZONE_C:
                dockingStation = new ZoneCGateDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(180);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                supportedPlaneTypes.addAll(Arrays.asList(
                        PlaneType.SINGLE_ENGINE
                ));
                supportedFlightTypes.addAll(Arrays.asList(
                        FlightType.PASSENGER,
                        FlightType.COMMERCIAL,
                        FlightType.PRIVATE
                ));
                break;
            case GENERAL_DOCKING_STATION:
                dockingStation = new GeneralDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(240);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService()));
                supportedPlaneTypes.addAll(Arrays.asList(
                        PlaneType.JET,
                        PlaneType.TURBOPROP,
                        PlaneType.SINGLE_ENGINE
                ));
                supportedFlightTypes.addAll(Arrays.asList(
                        FlightType.PASSENGER,
                        FlightType.COMMERCIAL,
                        FlightType.PRIVATE
                ));
                break;
            case LONG_DURATION:
                dockingStation = new LongDurationDockingStation();
                dockingStation.setMaxLandingDurationInMinutes(600);
                offeredServicesSet.addAll(Arrays.asList(
                        new RefuelingOfferedService(),
                        new CleaningOfferedService(),
                        new PassengerTransitOfferedService(),
                        new LoadingUnloadingOfferedService()));
                supportedPlaneTypes.addAll(Arrays.asList(
                        PlaneType.JET,
                        PlaneType.TURBOPROP,
                        PlaneType.SINGLE_ENGINE
                ));
                supportedFlightTypes.addAll(Arrays.asList(
                        FlightType.COMMERCIAL,
                        FlightType.PRIVATE
                ));
                break;
            default:
                throw new DockingStationException("The Docking Station Type specified does not exist.");
        }
        dockingStation.setOfferedServiceSet(offeredServicesSet);
        dockingStation.setSupportedPlaneTypeSet(supportedPlaneTypes);
        dockingStation.setSupportedFlightTypeSet(supportedFlightTypes);
        return this;
    }

    public DockingStationBuilder ofIdPrefixAndNumberOfSpaces(String namePrefix, int numberOfSpaces)
            throws DockingStationException{
        if(numberOfSpaces <= 0){
            throw new DockingStationException("Docking Station should have a positive number of docking spaces.");
        }
        List<DockingSpace> dockingSpaceList = new ArrayList<>();
        for(int count=0; count < numberOfSpaces; count++){
            DockingSpace dockingSpace = new DockingSpace();
            dockingSpace.setDockingSpaceState(DockingSpaceState.EMPTY);
            dockingSpace.setId(namePrefix + count);
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
                dockingStation.getOfferedServiceSet() != null &&
                dockingStation.getSupportedFlightTypeSet() != null &&
                dockingStation.getSupportedPlaneTypeSet() != null);
    }

}
