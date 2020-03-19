package gr.ntua.multimediaproject.airport;

import gr.ntua.multimediaproject.airport.exceptions.AirportException;
import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.commonutils.TimerCounter;
import gr.ntua.multimediaproject.dockingstations.DockingSpace;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AirportSingleton {
    private static AirportSingleton airport;
    private int timeElapsedInMinutes;
    private Thread timerCounterThread;
    private double totalMoneyEarned;

    private List<DockingStation> dockingStationList;
    private List<Flight> holdingFlightsList;
    private List<Flight> landingOrParkedFlightsList;
    private Map<Flight, Thread> landingOrParkedFlightThreadsMap;

    public static AirportSingleton getInstance(){
        if(airport == null){
            airport = new AirportSingleton();
        }
        return airport;
    }

    private AirportSingleton(){
        dockingStationList = new CopyOnWriteArrayList<>();
        holdingFlightsList = new CopyOnWriteArrayList<>();
        landingOrParkedFlightsList = new CopyOnWriteArrayList<>();
        landingOrParkedFlightThreadsMap = new ConcurrentHashMap<>();
        totalMoneyEarned = 0;
        timeElapsedInMinutes = 0;
        startTimerCounter();
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

    public List<Flight> getLandingOrParkedFlightsList() {
        return landingOrParkedFlightsList;
    }

    public void setLandingOrParkedFlightsList(List<Flight> landingOrParkedFlightsList) {
        this.landingOrParkedFlightsList = landingOrParkedFlightsList;
    }

    public int getTimeElapsedInMinutes() {
        return timeElapsedInMinutes;
    }

    public void setTimeElapsedInMinutes(int timeElapsedInMinutes) {
        this.timeElapsedInMinutes = timeElapsedInMinutes;
    }

    public double getTotalMoneyEarned() {
        return totalMoneyEarned;
    }

    public void setTotalMoneyEarned(double totalMoneyEarned) {
        this.totalMoneyEarned = totalMoneyEarned;
    }

    public Map<Flight, Thread> getLandingOrParkedFlightThreadsMap() {
        return landingOrParkedFlightThreadsMap;
    }

    public void setLandingOrParkedFlightThreadsMap(Map<Flight, Thread> landingOrParkedFlightThreadsMap) {
        this.landingOrParkedFlightThreadsMap = landingOrParkedFlightThreadsMap;
    }

    public void initializeAirportContentFromFiles() throws DockingStationException{
        String airportSetupFileName = "Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/airport_default.txt";
        initializeDockingStationListFromAirportSetupFile(airportSetupFileName);
        if(!AbstractHelper.canLoopCollection(dockingStationList)){
            throw new DockingStationException("Airport has no Docking Stations!");
        }
        System.out.println(timeElapsedInMinutes + " - INFO: Airport completed initialization of Docking Stations from file.");
        System.out.println(timeElapsedInMinutes + " - INFO: Airport number of Docking Stations: " + dockingStationList.size());

        String flightsSetupFileName = "Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/setup_default.txt";
        initializeStartingFlightsFromAirportSetupFile(flightsSetupFileName);
        System.out.println(timeElapsedInMinutes + " - INFO: Airport completed initialization of starting Flights from file.");
        System.out.println(timeElapsedInMinutes + " - INFO: Airport number of starting Flights: " + holdingFlightsList.size());
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
            System.out.println(timeElapsedInMinutes + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
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
                System.out.println(timeElapsedInMinutes + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(timeElapsedInMinutes + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
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
            System.out.println(timeElapsedInMinutes + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
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
            System.out.println(timeElapsedInMinutes + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }

    private Flight createNewFlightFromFileLine(String[] line) throws FlightException{
        try{
            String id = line[0];
            String city = line[1];
            FlightType flightType = FlightType.valueOf(Integer.parseInt(line[2]));
            PlaneType planeType = PlaneType.valueOf(Integer.parseInt(line[3]));
            int predictedTakeoffTimeInMinutes = Integer.parseInt(line[4]);

            Flight flight = FlightBuilder.create().ofId(id).ofCity(city).ofPlaneType(planeType).ofFlightType(flightType).
                    ofFlightState(FlightState.HOLDING).ofPredictedParkingDurationInMinutes(predictedTakeoffTimeInMinutes).
                    ofInitialCommunicationTime(0).ofRequestedOfferedServicesSet(Collections.EMPTY_SET).ofAirport(this).build();
            System.out.println(airport.getTimeElapsedInMinutes() + " - INFO: FlightBuilder has created flight: \"" + flight +"\".");
            return flight;
        }
        catch(FlightException ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            throw new FlightException("createNewFlightFromFileLine failed.");
        }
    }

    public synchronized void landHoldingFlightsInFreeLandingSpaces(){
        for (Flight flight : holdingFlightsList) {
            DockingStation appropriateDockingStation = findAppropriateDockingStationWithMostDockingSpaces(flight);
            if (appropriateDockingStation != null) {
                try {
                    associateFlightWithDockingStation(flight, appropriateDockingStation);
                    Thread thread = new Thread(flight, "Flight" + flight.getId());
                    landingOrParkedFlightThreadsMap.put(flight, thread);
                    thread.start();
                } catch (AirportException ex) {
                    System.out.println(timeElapsedInMinutes + " - ERROR: " + ex + ": " + ex.getMessage() + " " + ex.getCause());
                    System.out.println(timeElapsedInMinutes + " - INFO: Deleting flight with id: \"" + flight.getId() + "\" after failed association with Docking Space...");
                }
            }
        }
    }

    private DockingStation findAppropriateDockingStationWithMostDockingSpaces(Flight flight){
        int largestNumberOfDockingSpaces = 0;
        DockingStation dockingStationWithMostDockingSpaces = null;
        for(DockingStation dockingStation : dockingStationList){
            if(dockingStation.canServiceFlightType(flight.getFlightType()) &&
                    dockingStation.getMaxLandingDurationInMinutes() >= flight.getPredictedParkingDurationInMinutes() &&
                    dockingStation.canServicePlaneType(flight.getPlaneType()) &&
                    dockingStation.hasOfferedServices(flight.getRequestedOfferedServicesSet()) &&
                    dockingStation.getNumberOfFreeDockingSpaces() > largestNumberOfDockingSpaces){
                largestNumberOfDockingSpaces = dockingStation.getNumberOfFreeDockingSpaces();
                dockingStationWithMostDockingSpaces = dockingStation;
            }
        }
//        System.out.println("DEBUG: Flight: " + flight.getId() + " got Docking Station with Docking Space " +
//                (dockingStationWithMostDockingSpaces != null ? dockingStationWithMostDockingSpaces.getDockingSpaceList().get(0).getId() : "null")
//                + " and space " +
//                (dockingStationWithMostDockingSpaces != null ? dockingStationWithMostDockingSpaces.getNumberOfFreeDockingSpaces() : "null"));
        return dockingStationWithMostDockingSpaces;
    }



    private void associateFlightWithDockingStation(Flight flight, DockingStation dockingStation) throws AirportException{
        DockingSpace firstAvailableDockingSpace = null;
        for(DockingSpace dockingSpace : dockingStation.getDockingSpaceList()){
            if(dockingSpace.getFlight() == null){
                firstAvailableDockingSpace = dockingSpace;
                if(dockingStation.getNumberOfFreeDockingSpaces() == 0){
                    throw new AirportException("Number of free Docking Spaces in appropriate Docking Station was 0 when they should be at least 1.");
                }
                dockingStation.setNumberOfFreeDockingSpaces(dockingStation.getNumberOfFreeDockingSpaces() - 1);
                break;
            }
        }
        if(firstAvailableDockingSpace == null){
            throw new AirportException("firstAvailableDockingSpace was null despite Docking Station being listed as appropriate.");
        }
        flight.setDockingSpace(firstAvailableDockingSpace);
        //TODO add actualTakeoffTimeInApplicationMinutes to flight
        firstAvailableDockingSpace.setFlight(flight);
        holdingFlightsList.remove(flight);
        landingOrParkedFlightsList.add(flight);
    }

    public synchronized void earnMoneyFromFlight(Flight flight){
        double moneyToAdd = 0;
        if(flight != null && flight.getDockingSpace() != null && flight.getDockingSpace().getDockingStation() != null){
            moneyToAdd = flight.getDockingSpace().getDockingStation().getCost();
        }
        else{
            System.out.println(timeElapsedInMinutes + " - ERROR: money added from flight is 0...");
            return;
        }
        double serviceMultiplier = 1;
        if(flight.getRequestedOfferedServicesSet() != null){
            for(OfferedService offeredService : flight.getRequestedOfferedServicesSet()){
                serviceMultiplier += offeredService.getExtraCostFactor();
            }
        }
        moneyToAdd *= serviceMultiplier;
        double delayMultiplier = 1;
        int predictedParkingDurationInMinutes = flight.getPredictedParkingDurationInMinutes();
        if(predictedParkingDurationInMinutes > 0){
            int actualParkingDurationInMinutes = predictedParkingDurationInMinutes + flight.getDelayTimeInMinutes();
            if(actualParkingDurationInMinutes > predictedParkingDurationInMinutes){
                delayMultiplier += 1;
            }
            else if((actualParkingDurationInMinutes <= predictedParkingDurationInMinutes - 10) &&
                    (actualParkingDurationInMinutes > predictedParkingDurationInMinutes - 25)){
                delayMultiplier -= 0.1;
            }
            else if(actualParkingDurationInMinutes <= predictedParkingDurationInMinutes - 25){
                delayMultiplier -= 0.2;
            }
        }
        else{
            System.out.println(timeElapsedInMinutes + " - ERROR: flight had non-positive predicted parking duration when calculating payment...");
            return;
        }
        moneyToAdd *= delayMultiplier;
        totalMoneyEarned += moneyToAdd;
        System.out.println(timeElapsedInMinutes + " - INFO: " + moneyToAdd + " money added from flight with id: " + flight.getId());
    }

    private void startTimerCounter(){
        TimerCounter timerCounter = new TimerCounter(this);
        timerCounterThread = new Thread(timerCounter, "TimerCounter");
        timerCounterThread.start();
    }

    public synchronized void incrementTimeElapsedInMinutes(){
        timeElapsedInMinutes++;
    }

    public void clearAirport(){
        System.out.println(timeElapsedInMinutes + " - INFO: Airport resetting...");
        for(Thread thread : landingOrParkedFlightThreadsMap.values()){
            thread.interrupt();
        }
        try {
            Thread.sleep(10);       //wait for threads to stop
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        landingOrParkedFlightThreadsMap = new ConcurrentHashMap<>();
        dockingStationList = new CopyOnWriteArrayList<>();
        holdingFlightsList = new CopyOnWriteArrayList<>();
        landingOrParkedFlightsList = new CopyOnWriteArrayList<>();
        totalMoneyEarned = 0;
        interruptTimerCounter();
        timeElapsedInMinutes = 0;
        System.out.println(timeElapsedInMinutes + " - INFO: Airport has been reset...");
        System.out.println(Thread.getAllStackTraces().keySet());
    }

    private void interruptTimerCounter(){
        timerCounterThread.interrupt();
    }

    public List<Flight> getFlightsDepartingIn10Minutes(){
        List<Flight> flightsDepartingIn10Minutes = new ArrayList<>();
        for(Flight flight : landingOrParkedFlightsList){
            int predictedTakeoffTime = flight.getActualTakeoffTimeInApplicationMinutes() - flight.getDelayTimeInMinutes();
            if(predictedTakeoffTime <= timeElapsedInMinutes + 10){
                flightsDepartingIn10Minutes.add(flight);
            }
        }
        return flightsDepartingIn10Minutes;
    }

    public List<Flight> getDelayedFlights(){
        List<Flight> delayedFlights = new ArrayList<>();
        for(Flight flight : landingOrParkedFlightsList){
            int predictedTakeoffTime = flight.getActualTakeoffTimeInApplicationMinutes() - flight.getDelayTimeInMinutes();
            if(predictedTakeoffTime > timeElapsedInMinutes){
                delayedFlights.add(flight);
            }
        }
        return delayedFlights;
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
                "timeElapsedInMinutes=" + timeElapsedInMinutes + "\n" +
                "totalMoneyEarned=" + totalMoneyEarned + "\n" +
//                "dockingStationList=\n\n" + dockingStationListToString +
//                "holdingFlightsList=\n\n" + holdingFlightsToString +
                '}';
    }
}
