package gr.ntua.multimediaproject.stations;

import gr.ntua.multimediaproject.flights.FlightType;
import gr.ntua.multimediaproject.flights.PlaneType;
import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.util.Objects;
import java.util.Set;

public abstract class DockingStation {
    protected String id;
    protected int numberOfLandingSpaces;
    protected DockingStationState stationState;
    protected int maxLandingDurationInMinutes;
    protected double cost;
    protected Set<OfferedService> offeredServices;
    protected Set<FlightType> supportedFlightTypes;
    protected Set<PlaneType> supportedPlaneTypes;

    DockingStation(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberOfLandingSpaces() {
        return numberOfLandingSpaces;
    }

    public void setNumberOfLandingSpaces(int numberOfLandingSpaces) {
        this.numberOfLandingSpaces = numberOfLandingSpaces;
    }

    public DockingStationState getStationState() {
        return stationState;
    }

    public void setStationState(DockingStationState stationState) {
        this.stationState = stationState;
    }

    public int getMaxLandingDurationInMinutes() {
        return maxLandingDurationInMinutes;
    }

    public void setMaxLandingDurationInMinutes(int maxLandingDurationInMinutes) {
        this.maxLandingDurationInMinutes = maxLandingDurationInMinutes;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Set<OfferedService> getOfferedServices() {
        return offeredServices;
    }

    public void setOfferedServices(Set<OfferedService> offeredServices) {
        this.offeredServices = offeredServices;
    }

    public Set<FlightType> getSupportedFlightTypes() {
        return supportedFlightTypes;
    }

    public void setSupportedFlightTypes(Set<FlightType> supportedFlightTypes) {
        this.supportedFlightTypes = supportedFlightTypes;
    }

    public Set<PlaneType> getSupportedPlaneTypes() {
        return supportedPlaneTypes;
    }

    public void setSupportedPlaneTypes(Set<PlaneType> supportedPlaneTypes) {
        this.supportedPlaneTypes = supportedPlaneTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DockingStation that = (DockingStation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DockingStation{" +
                "type=" + this.getClass().toString() +
                "id='" + id + '\'' +
                ", numberOfLandingSpaces=" + numberOfLandingSpaces +
                ", stationState=" + stationState +
                ", maxLandingDurationInMinutes=" + maxLandingDurationInMinutes +
                ", cost=" + cost +
                ", offeredServices=" + offeredServices +
                '}';
    }
}
