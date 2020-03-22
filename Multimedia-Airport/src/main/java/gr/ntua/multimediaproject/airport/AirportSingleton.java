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
import gr.ntua.multimediaproject.offeredservices.*;
import gr.ntua.multimediaproject.ui.MainWindow;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AirportSingleton {
    private static AirportSingleton airport;
    private MainWindow mainWindow;

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
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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

    public void initializeAirportContentFromFiles(String scenarioId) throws DockingStationException, AirportException{
        String airportSetupFileName = "Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/airport_" + scenarioId + ".txt";
        initializeDockingStationListFromAirportSetupFile(airportSetupFileName);
        if(!AbstractHelper.canLoopCollection(dockingStationList)){
            throw new DockingStationException("Airport has no Docking Stations!");
        }
        System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport completed initialization of Docking Stations from file.");
        Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport completed initialization of Docking Stations from file."));
        System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport number of Docking Stations: " + dockingStationList.size());

        String flightsSetupFileName = "Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/setup_" + scenarioId + ".txt";
        initializeStartingFlightsFromAirportSetupFile(flightsSetupFileName);
        System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport completed initialization of starting Flights from file.");
        Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport completed initialization of starting Flights from file."));
        System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport number of starting Flights: " + holdingFlightsList.size());
    }


    private void initializeDockingStationListFromAirportSetupFile(String fileName) throws AirportException{
        List<String[]> lines = readFileLinesIntoListOfStringArrays(fileName, 4);
        try {
            for (String[] line : lines) {
                DockingStation dockingStation = createNewDockingStationsFromFileLine(line);
                dockingStationList.add(dockingStation);
            }
        }
        catch(DockingStationException ex){
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause()));
        }
    }


    private List<String[]> readFileLinesIntoListOfStringArrays(String fileName, int numberOfItemsPerLine) throws AirportException{
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
                System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
                Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause()));
                throw new AirportException(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: IO exception in file " + fileName + "...");
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause()));
            throw new AirportException(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: File " + fileName + " not found...");
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
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause()));
            throw new DockingStationException("createNewDockingStationsFromFileLine failed.");
        }
    }


    private void initializeStartingFlightsFromAirportSetupFile(String flightsSetupFileName) throws AirportException{
        List<String[]> lines = readFileLinesIntoListOfStringArrays(flightsSetupFileName, 5);
        try {
            for (String[] line : lines) {
                Flight flight = createNewFlightFromFileLine(line);
                if(flightIdAlreadyExists(flight)){
                    System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: Flight with id " + flight.getId() + " already exists. Ignoring flight...");
                    Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: Flight with id " + flight.getId() + " already exists. Ignoring flight..."));
                    continue;
                }
                holdingFlightsList.add(flight);
            }
        }
        catch(FlightException ex){
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause()));
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }

    private boolean flightIdAlreadyExists(Flight flight){
        for(Flight holdingFlight : holdingFlightsList){
            if(flight.getId().equals(holdingFlight.getId())) {
                return true;
            }
        }
        for(Flight landingOrParkedFlight : landingOrParkedFlightsList) {
            if (landingOrParkedFlight.getId().equals(flight.getId())) {
                return true;
            }
        }
        return false;
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
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) +
                    " - INFO: FlightBuilder has created flight: \"" + flight +"\".");
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) +
                    " - INFO: FlightBuilder has created flight: \"" + flight +"\"."));
            return flight;
        }
        catch(FlightException ex){
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause()));
            throw new FlightException("createNewFlightFromFileLine failed.");
        }
    }

    public synchronized void landHoldingFlightsInFreeLandingSpaces() throws AirportException{
        for (Flight flight : holdingFlightsList) {
            DockingStation appropriateDockingStation = findAppropriateDockingStationWithMostDockingSpaces(flight);
            if (appropriateDockingStation != null) {
                try {
                    associateFlightWithDockingStation(flight, appropriateDockingStation);
                    Thread thread = new Thread(flight, "Flight" + flight.getId());
                    landingOrParkedFlightThreadsMap.put(flight, thread);
                    thread.start();
                } catch (AirportException ex) {
                    System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": " + ex.getMessage() + " " + ex.getCause());
                    Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: " + ex + ": " + ex.getMessage() + " " + ex.getCause()));
                    System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Deleting flight with id: \"" + flight.getId() + "\" after failed association with Docking Space...");
                    Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Deleting flight with id: \"" + flight.getId() + "\" after failed association with Docking Space..."));
                }
            }
        }
    }

    private DockingStation findAppropriateDockingStationWithMostDockingSpaces(Flight flight) throws AirportException{
        int largestNumberOfDockingSpaces = 0;
        DockingStation dockingStationWithMostDockingSpaces = null;
        for(DockingStation dockingStation : dockingStationList){
            if(dockingStation.canServiceFlightType(flight.getFlightType()) &&
                    (dockingStation.getMaxLandingDurationInMinutes() >= flight.getPredictedParkingDurationInMinutes()) &&
                    dockingStation.canServicePlaneType(flight.getPlaneType()) &&
                    dockingStation.hasOfferedServices(flight.getRequestedOfferedServicesSet()) &&
                    dockingStation.getNumberOfFreeDockingSpaces() > largestNumberOfDockingSpaces){
                largestNumberOfDockingSpaces = dockingStation.getNumberOfFreeDockingSpaces();
                dockingStationWithMostDockingSpaces = dockingStation;
            }
        }
        return dockingStationWithMostDockingSpaces;
    }

    private boolean flightCanBeServiced(Flight flight) throws AirportException{
        for(DockingStation dockingStation : dockingStationList) {
            if (dockingStation.canServiceFlightType(flight.getFlightType()) &&
                    (dockingStation.getMaxLandingDurationInMinutes() >= flight.getPredictedParkingDurationInMinutes()) &&
                    dockingStation.canServicePlaneType(flight.getPlaneType()) &&
                    dockingStation.hasOfferedServices(flight.getRequestedOfferedServicesSet())) {
                return true;
            }
        }
        return false;
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
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: money added from flight is 0..."));
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: money added from flight is 0...");
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
            Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: money added from flight is 0..."));
            System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - ERROR: money added from flight is 0...");
            return;
        }
        moneyToAdd *= delayMultiplier;
        totalMoneyEarned += moneyToAdd;
        System.out.println(timeElapsedInMinutes + " - INFO: " + moneyToAdd + " money added from flight with id: " + flight.getId());
        final double finalMoneyToAdd = moneyToAdd;
        Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: " + finalMoneyToAdd + " money added from flight with id: " + flight.getId()));
    }

    private void startTimerCounter(){
        TimerCounter timerCounter = new TimerCounter(this);
        timerCounterThread = new Thread(timerCounter, "TimerCounter");
        timerCounterThread.start();
    }

    public synchronized void incrementTimeElapsedInMinutes(){
        timeElapsedInMinutes++;
        Platform.runLater(() -> mainWindow.refreshGui());
    }

    public void clearAirport(){
        System.out.println(timeElapsedInMinutes + " - INFO: Airport resetting...");
        Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport resetting..."));
        for(Thread thread : landingOrParkedFlightThreadsMap.values()){
            thread.interrupt();
        }
        do {
            totalMoneyEarned = 0;
            try {
                Thread.sleep(50);       //wait for threads to stop
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(totalMoneyEarned != 0);
        landingOrParkedFlightThreadsMap = new ConcurrentHashMap<>();
        dockingStationList = new CopyOnWriteArrayList<>();
        holdingFlightsList = new CopyOnWriteArrayList<>();
        landingOrParkedFlightsList = new CopyOnWriteArrayList<>();
        interruptTimerCounter();
        timeElapsedInMinutes = 0;
        System.out.println(timeElapsedInMinutes + " - INFO: Airport has been reset...");
        Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) + " - INFO: Airport has been reset..."));
        System.out.println(Thread.getAllStackTraces().keySet());
    }

    public void runAirport(String scenarioId) throws DockingStationException, AirportException {
        initializeAirportContentFromFiles(scenarioId);
        startTimerCounter();
        landHoldingFlightsInFreeLandingSpaces();
    }

    private void interruptTimerCounter(){
        if(timerCounterThread != null) {
            timerCounterThread.interrupt();
        }
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

    public void createNewFlight(String id, String city, String flightTypeString, String planeTypeString, int predictedParkingDuration,
                                boolean cleaning, boolean loading, boolean transit, boolean refueling) throws FlightException, AirportException {
        Set<OfferedService> requiredServices = new HashSet<>();
        if(cleaning){
            requiredServices.add(CleaningOfferedServiceSingleton.getInstance());
        }
        if(loading){
            requiredServices.add(LoadingUnloadingOfferedServiceSingleton.getInstance());
        }
        if(transit){
            requiredServices.add(PassengerTransitOfferedServiceSingleton.getInstance());
        }
        if(refueling){
            requiredServices.add(RefuelingOfferedServiceSingleton.getInstance());
        }
        PlaneType planeType = PlaneType.valueOf(Integer.parseInt(planeTypeString));
        FlightType flightType = FlightType.valueOf(Integer.parseInt(flightTypeString));
        Flight flight = FlightBuilder.create().ofId(id).ofCity(city).ofPlaneType(planeType).ofFlightType(flightType).
                ofFlightState(FlightState.HOLDING).ofPredictedParkingDurationInMinutes(predictedParkingDuration).
                ofInitialCommunicationTime(timeElapsedInMinutes).ofRequestedOfferedServicesSet(requiredServices).ofAirport(this).build();
        System.out.println(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) +
                " - INFO: FlightBuilder has created flight: \"" + flight +"\".");
        Platform.runLater(() -> mainWindow.logMessage(AbstractHelper.minutesToHoursMinutes(timeElapsedInMinutes) +
                " - INFO: FlightBuilder has created flight: \"" + flight +"\"."));
        if(flightIdAlreadyExists(flight)){
            throw new AirportException("Flight ID already exists.");
        }
        if(!flightCanBeServiced(flight)){
            throw new AirportException("No Docking Station is qualified to service this flight. Please check parameter " +
                    "combination.");
        }
        holdingFlightsList.add(flight);
        landHoldingFlightsInFreeLandingSpaces();
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
                "dockingStationNumber=" + (dockingStationList != null ? dockingStationList.size() + "\n" : "null\n") +
                "holdingFlightsNumber=" + (holdingFlightsList != null ? holdingFlightsList.size() + "\n": "null\n") +
                "landingOrParkedFlightsNumber=" + (landingOrParkedFlightsList != null ? landingOrParkedFlightsList.size() + "\n": "null\n") +
//                "dockingStationList=\n\n" + dockingStationListToString +
//                "holdingFlightsList=\n\n" + holdingFlightsToString +
                '}';
    }
}
