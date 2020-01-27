package gr.ntua.multimediaproject.airport;

import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.dockingstations.DockingStation;
import gr.ntua.multimediaproject.dockingstations.DockingStationBuilder;
import gr.ntua.multimediaproject.dockingstations.DockingStationType;
import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;
import gr.ntua.multimediaproject.flights.*;
import gr.ntua.multimediaproject.flights.exceptions.FlightException;
import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AirportSingleton {
    private static AirportSingleton airport;

    private List<DockingStation> dockingStationList;
    private List<Flight> holdingFlightsList;

    public static AirportSingleton getInstance(){
        if(airport == null){
            airport = new AirportSingleton();
        }
        return airport;
    }

    private AirportSingleton(){
        dockingStationList = new ArrayList<>();
        holdingFlightsList = new ArrayList<>();
    }

    public List<DockingStation> getDockingStationList() {
        return dockingStationList;
    }

    public void setDockingStationList(List<DockingStation> dockingStationList) {
        this.dockingStationList = dockingStationList;
    }

    public List<Flight> getHoldingFlightsList() {
        return holdingFlightsList;
    }

    public void setHoldingFlightsList(List<Flight> holdingFlightsList) {
        this.holdingFlightsList = holdingFlightsList;
    }


    public void initializeAirportContentFromFiles() throws DockingStationException{
        String airportSetupFileName = "Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/airport_default.txt";
        initializeDockingStationListFromAirportSetupFile(airportSetupFileName);
        if(!AbstractHelper.canLoopCollection(dockingStationList)){
            throw new DockingStationException("Airport has no Docking Stations!");
        }
        System.out.println("Airport number of Docking Stations: " + dockingStationList.size());

        String flightsSetupFileName = "Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/setup_default.txt";
        initializeStartingFlightsFromAirportSetupFile(flightsSetupFileName);
        System.out.println("Airport number of starting flights: " + holdingFlightsList.size());
    }


    private void initializeDockingStationListFromAirportSetupFile(String fileName){
        List<String[]> lines = readFileLinesIntoListOfStringArrays(fileName, 4);
        try {
            for (String[] line : lines) {
                DockingStation dockingStation = createNewDockingStationsFromFileLine(line);
                dockingStationList.add(dockingStation);
            }
        }
        catch(DockingStationException ex){
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }


    private List<String[]> readFileLinesIntoListOfStringArrays(String fileName, int numberOfItemsPerLine){
        List<String[]> lines = new ArrayList<>();
        String delimiter = ",(\\s*)";
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    String[] splitLine = line.split(delimiter);
                    if(splitLine.length != numberOfItemsPerLine){
                        throw new IOException("The line \"" + Arrays.toString(splitLine) + "\" did not have length 4. All lines should" +
                                " have 4 arguments.");
                    }
                    lines.add(splitLine);
                }
            }
            catch (IOException ex) {
                System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
        return lines;
    }


    private DockingStation createNewDockingStationsFromFileLine(String[] line) throws DockingStationException{
        try {
            DockingStationType dockingStationType = DockingStationType.valueOf(Integer.parseInt(line[0]));
            int numberOfLandingSpaces = Integer.parseInt(line[1]);
            double cost = Double.parseDouble(line[2]);
            String idPrefix = line[3];
            return DockingStationBuilder.create().ofDockingStationType(dockingStationType).ofCost(cost).
                    ofIdPrefixAndNumberOfSpaces(idPrefix, numberOfLandingSpaces).build();
        }
        catch(DockingStationException ex){
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
            throw new DockingStationException("createNewDockingStationsFromFileLine failed.");
        }
    }


    private void initializeStartingFlightsFromAirportSetupFile(String flightsSetupFileName){
        List<String[]> lines = readFileLinesIntoListOfStringArrays(flightsSetupFileName, 5);
        try {
            for (String[] line : lines) {
                Flight flight = createNewFlightFromFileLine(line);
                holdingFlightsList.add(flight);
            }
        }
        catch(FlightException ex){
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }


    private Flight createNewFlightFromFileLine(String[] line) throws FlightException{
        try{
            String id = line[0];
            String city = line[1];
            FlightType flightType = FlightType.valueOf(Integer.parseInt(line[2]));
            PlaneType planeType = PlaneType.valueOf(Integer.parseInt(line[3]));
            int predictedTakeoffTimeInMinutes = Integer.parseInt(line[4]);

            return FlightBuilder.create().ofId(id).ofCity(city).ofPlaneType(planeType).ofFlightType(flightType).
                    ofFlightState(FlightState.HOLDING).ofPredictedTakeoffTimeInMinutes(predictedTakeoffTimeInMinutes).
                    ofRequestedOfferedServicesSet(Collections.EMPTY_SET).build();

        }
        catch(FlightException ex){
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
            throw new FlightException("createNewFlightFromFileLine failed.");
        }
    }

    //TODO create function that looks for docking station for every plane in holdingFlights (or another idea, keep in mind threading)
//    DockingStation dockingStation = findAvailableDockingStation(flight.getFlightType(), flight.getPlaneType(),
//            flight.getRequestedOfferedServicesSet());
//            dockingStation.dockFlight(flight);


    private DockingStation findAvailableDockingStation(FlightType flightType, PlaneType planeType,
                                                Set<OfferedService> requestedServiceSet){
        for(DockingStation dockingStation : dockingStationList){
            if(dockingStation.canServiceFlightType(flightType) &&
                    dockingStation.canServicePlaneType(planeType) &&
                    dockingStation.hasOfferedServices(requestedServiceSet) &&
                    dockingStation.hasFreeDockingSpaces()){
                return dockingStation;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        String dockingStationListToString = "";
        for(DockingStation dockingStation : dockingStationList){
            dockingStationListToString = dockingStationListToString.concat(dockingStation.toString() + "\n");
        }
        String holdingFlightsToString = "";
        for(Flight flight : holdingFlightsList){
            holdingFlightsToString = holdingFlightsToString.concat(flight.toString() + "\n");
        }
        return "AirportSingleton{\n" +
                "dockingStationList=\n\n" + dockingStationListToString +
                "holdingFlightsList=\n\n" + holdingFlightsToString +
                '}';
    }
}
