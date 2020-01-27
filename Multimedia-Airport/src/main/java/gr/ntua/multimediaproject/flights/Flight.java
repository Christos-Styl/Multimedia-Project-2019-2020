package gr.ntua.multimediaproject.flights;

import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Flight {
    private static Random random = new Random(System.currentTimeMillis());

    private String id;
    private String city;
    private FlightType flightType;
    private PlaneType planeType;
    private FlightState flightState;
    private int predictedTakeoffTimeInMinutes;
    private Set<OfferedService> requestedOfferedServicesSet;
    private int timeNeededToDockInMinutes;

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

    public int getPredictedTakeoffTimeInMinutes() {
        return predictedTakeoffTimeInMinutes;
    }

    public void setPredictedTakeoffTimeInMinutes(int predictedTakeoffTimeInMinutes) {
        this.predictedTakeoffTimeInMinutes = predictedTakeoffTimeInMinutes;
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

    public int getRandomTimeUntilDepartureInMinutes(int predictedTimeUntilDepartureInMinutes){
        //TODO fix so that it returns proper numbers (ex. bigger than predicted dep time)
        return random.ints(1, predictedTimeUntilDepartureInMinutes).findFirst().getAsInt();
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", flightType=" + flightType +
                ", planeType=" + planeType +
                ", flightState=" + flightState +
                ", predictedTakeoffTimeInMinutes=" + predictedTakeoffTimeInMinutes +
                ", requestedOfferedServicesSet=" + requestedOfferedServicesSet +
                ", timeNeededToDockInMinutes=" + timeNeededToDockInMinutes +
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
