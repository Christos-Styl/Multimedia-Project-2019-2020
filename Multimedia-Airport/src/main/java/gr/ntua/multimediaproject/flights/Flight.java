package gr.ntua.multimediaproject.flights;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import gr.ntua.multimediaproject.commonutils.AbstractHelper;
import gr.ntua.multimediaproject.dockingstations.DockingSpace;
import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;
import gr.ntua.multimediaproject.flights.exceptions.FlightException;
import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.util.Objects;
import java.util.Set;

public class Flight implements Runnable{
    private String id;
    private String city;
    private FlightType flightType;
    private PlaneType planeType;
    private FlightState flightState;
    private int predictedParkingDurationInMinutes;
    private int actualTakeoffTimeInApplicationMinutes;
    private int delayTimeInMinutes;
    private int initialCommunicationTime;
    private Set<OfferedService> requestedOfferedServicesSet;
    private int timeNeededToDockInMinutes;
    private DockingSpace dockingSpace;
    private AirportSingleton airport;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }

    public void setPlaneType(PlaneType planeType) {
        this.planeType = planeType;
    }

    public FlightState getFlightState() {
        return flightState;
    }

    public void setFlightState(FlightState flightState) {
        this.flightState = flightState;
    }

    public int getPredictedParkingDurationInMinutes() {
        return predictedParkingDurationInMinutes;
    }

    public void setPredictedParkingDurationInMinutes(int predictedParkingDurationInMinutes) {
        this.predictedParkingDurationInMinutes = predictedParkingDurationInMinutes;
    }

    public int getDelayTimeInMinutes() {
        return delayTimeInMinutes;
    }

    public void setDelayTimeInMinutes(int delayTimeInMinutes) {
        this.delayTimeInMinutes = delayTimeInMinutes;
    }

    public int getInitialCommunicationTime() {
        return initialCommunicationTime;
    }

    public void setInitialCommunicationTime(int initialCommunicationTime) {
        this.initialCommunicationTime = initialCommunicationTime;
    }

    public Set<OfferedService> getRequestedOfferedServicesSet() {
        return requestedOfferedServicesSet;
    }

    public void setRequestedOfferedServicesSet(Set<OfferedService> requestedOfferedServicesSet) {
        this.requestedOfferedServicesSet = requestedOfferedServicesSet;
    }

    public int getTimeNeededToDockInMinutes() {
        return timeNeededToDockInMinutes;
    }

    public void setTimeNeededToDockInMinutes(int timeNeededToDockInMinutes) {
        this.timeNeededToDockInMinutes = timeNeededToDockInMinutes;
    }

    public DockingSpace getDockingSpace() {
        return dockingSpace;
    }

    public void setDockingSpace(DockingSpace dockingSpace) {
        this.dockingSpace = dockingSpace;
    }

    public int getActualTakeoffTimeInApplicationMinutes() {
        return actualTakeoffTimeInApplicationMinutes;
    }

    public void setActualTakeoffTimeInApplicationMinutes(int actualTakeoffTimeInApplicationMinutes) {
        this.actualTakeoffTimeInApplicationMinutes = actualTakeoffTimeInApplicationMinutes;
    }

    public AirportSingleton getAirport() {
        return airport;
    }

    public void setAirport(AirportSingleton airport) {
        this.airport = airport;
    }

    @Override
    public void run() {
        if(!flightAttributesAreOkToLand()){
            System.out.println("ERROR: Flight attributes in flight with id: \"" + id + "\" were not OK before landing. Removing flight...");
            tryDeleteFlight();
            return;
        }
        actualTakeoffTimeInApplicationMinutes = airport.getTimeElapsedInMinutes() + timeNeededToDockInMinutes +
                predictedParkingDurationInMinutes + delayTimeInMinutes;
        flightState = FlightState.LANDING;
        System.out.println(airport.getTimeElapsedInMinutes() + " - INFO: Flight with id: \"" + this.id + "\" state changed to LANDING. Docking Space id is: \""
                + dockingSpace.getId() + "\" and actualTakeoffTimeInApplicationMinutes is: " + actualTakeoffTimeInApplicationMinutes +
                ". System time is: " + System.currentTimeMillis());
        tryToCompleteLanding();
        if(!flightAttributesAreOkToStayParked()){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: Flight attributes in flight with id: \"" + id + "\" were not OK to stay parked. Removing flight...");
            tryDeleteFlight();
            return;
        }
        flightState = FlightState.PARKED;
        System.out.println(airport.getTimeElapsedInMinutes() + " - INFO: Flight with id: \"" + this.id + "\" state changed to PARKED. Docking Space id is: \""
                + dockingSpace.getId() + "\".");
        tryWaitForParkingStayToEnd();
        airport.earnMoneyFromFlight(this);
        System.out.println(airport.getTimeElapsedInMinutes() + " - INFO: Flight with id: \"" + this.id + "\" has TAKEN OFF. Money has been added to airport. Deleting Flight...");
        tryDeleteFlight();
        System.out.println(airport.getTimeElapsedInMinutes() + " - INFO: Flight with id: \"" + this.id + "\" deleted...");
    }

    private boolean flightAttributesAreOkToLand(){

        return (flightState == FlightState.HOLDING &&
                predictedParkingDurationInMinutes + delayTimeInMinutes > 0 &&
                timeNeededToDockInMinutes > 0 &&
                dockingSpace != null &&
                dockingSpace.getDockingStation() != null &&
                predictedParkingDurationInMinutes <= dockingSpace.getDockingStation().getMaxLandingDurationInMinutes() &&
                airport != null);
    }

    private void tryToCompleteLanding(){
        int timeToDock = timeNeededToDockInMinutes * AbstractHelper.getRealMillisecondsForOneSimulationMinute();
        try{
            Thread.sleep(timeToDock);
        }
        catch (InterruptedException ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }

    private void tryWaitForParkingStayToEnd(){
        int timeToStayParked = (predictedParkingDurationInMinutes + delayTimeInMinutes) *
                AbstractHelper.getRealMillisecondsForOneSimulationMinute();
        try{
            Thread.sleep(timeToStayParked);
        }
        catch (InterruptedException ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }

    private boolean flightAttributesAreOkToStayParked(){
//        System.out.println("DEBUG: flightAttributesAreOkToStayParked?: id: " + id + ", flightState: " + flightState + ", time: " +
//                (predictedParkingDurationInMinutes + delayTimeInMinutes) + ", dockingSpace: " + dockingSpace);
        return (flightState == FlightState.LANDING &&
                predictedParkingDurationInMinutes + delayTimeInMinutes > 0 &&
                dockingSpace != null);
    }

    private void tryDeleteFlight(){
        try {
            deleteFlight();
        }
        catch(FlightException ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }

    private void deleteFlight() throws FlightException{
        if(this.dockingSpace != null){
            this.dockingSpace.setFlight(null);
            try {
                this.dockingSpace.updateDockingStationAfterTakeoff();
            }
            catch (DockingStationException ex){
                System.out.println(airport.getTimeElapsedInMinutes() + " - ERROR: " + ex + ": "+ ex.getMessage() + " " + ex.getCause());
            }
            this.dockingSpace = null;
        }
        if(this.airport != null){
            this.airport.getLandingOrParkedFlightsList().remove(this);
            this.airport.getLandingOrParkedFlightThreadsMap().remove(this);
            this.airport.landHoldingFlightsInFreeLandingSpaces();
        }
        else{
            throw new FlightException("Flight with id: \"" + this.id + "\" had null airport...");
        }
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", flightType=" + flightType +
                ", planeType=" + planeType +
                ", flightState=" + flightState +
                ", predictedParkingDurationInMinutes=" + predictedParkingDurationInMinutes +
                ", delayTimeInMinutes=" + delayTimeInMinutes +
                ", initialCommunicationTime=" + initialCommunicationTime +
                ", requestedOfferedServicesSet=" + requestedOfferedServicesSet +
                ", timeNeededToDockInMinutes=" + timeNeededToDockInMinutes +
                ", dockingSpaceId='" + (dockingSpace == null? "null" : dockingSpace.getId()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id) &&
                Objects.equals(city, flight.city) &&
                flightType == flight.flightType &&
                planeType == flight.planeType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city);
    }
}
