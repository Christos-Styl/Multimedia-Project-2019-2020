package gr.ntua.multimedia.airport.stations;

import gr.ntua.multimedia.airport.offeredservices.OfferedService;

import java.util.Objects;
import java.util.Set;

enum DockingStationState {
    EMPTY,
    TAKEN
}

public abstract class DockingStation {
    protected String id;
    protected int numberOfLandingSpaces;
    protected DockingStationState stattionState;
    protected int maxLandingDurationInMinutes;
    protected double cost;
    protected Set<OfferedService> offeredServices;

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

    public DockingStationState getStattionState() {
        return stattionState;
    }

    public void setStattionState(DockingStationState stattionState) {
        this.stattionState = stattionState;
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
                "id='" + id + '\'' +
                ", numberOfLandingSpaces=" + numberOfLandingSpaces +
                ", stattionState=" + stattionState +
                ", maxLandingDurationInMinutes=" + maxLandingDurationInMinutes +
                ", cost=" + cost +
                ", offeredServices=" + offeredServices +
                '}';
    }
}
