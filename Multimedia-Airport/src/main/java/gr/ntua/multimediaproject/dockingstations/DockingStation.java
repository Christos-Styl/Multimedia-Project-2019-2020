package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.flights.FlightType;
import gr.ntua.multimediaproject.flights.PlaneType;
import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class DockingStation {
    protected int numberOfFreeDockingSpaces;
    protected List<DockingSpace> dockingSpaceList;
    protected int maxLandingDurationInMinutes;
    protected double cost;
    protected Set<OfferedService> offeredServiceSet;

    DockingStation(){}

    public synchronized int getNumberOfFreeDockingSpaces() {
        return numberOfFreeDockingSpaces;
    }

    public synchronized void setNumberOfFreeDockingSpaces(int numberOfFreeDockingSpaces) {
        this.numberOfFreeDockingSpaces = numberOfFreeDockingSpaces;
    }

    public List<DockingSpace> getDockingSpaceList() {
        return dockingSpaceList;
    }

    public void setDockingSpaceList(List<DockingSpace> dockingSpaceList) {
        this.dockingSpaceList = dockingSpaceList;
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

    public Set<OfferedService> getOfferedServiceSet() {
        return offeredServiceSet;
    }

    public void setOfferedServiceSet(Set<OfferedService> offeredServiceSet) {
        this.offeredServiceSet = offeredServiceSet;
    }

    public abstract boolean canServiceFlightType(FlightType flightType);

    public abstract boolean canServicePlaneType(PlaneType planeType);

    public boolean hasOfferedServices(Set<OfferedService> requestedServiceSet){
        return offeredServiceSet.containsAll(requestedServiceSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DockingStation that = (DockingStation) o;
        return Objects.equals(dockingSpaceList, that.dockingSpaceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dockingSpaceList);
    }

    @Override
    public String toString() {
        String dockingSpacesToString = "";
        for(DockingSpace dockingSpace : dockingSpaceList){
            dockingSpacesToString = dockingSpacesToString.concat(dockingSpace.toString() + "\n");
        }
        String offeredServicesToString = "";
        for(OfferedService offeredService : offeredServiceSet){
            offeredServicesToString = offeredServicesToString.concat(offeredService.toString() + " ");
        }
        return "DockingStation{" +
                "type=" + this.getClass().toString() +
                ", numberOfFreeDockingSpaces=" + numberOfFreeDockingSpaces +
                ", dockingSpaceList=\n" + dockingSpacesToString +
                ", maxLandingDurationInMinutes=" + maxLandingDurationInMinutes +
                ", cost=" + cost +
                ", offeredServiceSet=\n" + offeredServicesToString +
                "}\n";
    }
}
