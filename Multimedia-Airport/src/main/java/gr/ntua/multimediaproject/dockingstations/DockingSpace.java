package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;
import gr.ntua.multimediaproject.flights.Flight;

import java.util.Objects;

public class DockingSpace {
    private String id;
    private Flight flight;
    private DockingStation dockingStation;

    public DockingSpace() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public DockingStation getDockingStation() {
        return dockingStation;
    }

    public void setDockingStation(DockingStation dockingStation) {
        this.dockingStation = dockingStation;
    }

    public synchronized void updateDockingStationAfterTakeoff() throws DockingStationException{
        if(dockingStation.getNumberOfFreeDockingSpaces() + 1 > dockingStation.getDockingSpaceList().size()){
            throw new DockingStationException("Tried to free docking space in Docking Station with docking space : \""
                    + this.id + "\" but Docking Station should already be completely empty...");
        }
        this.dockingStation.setNumberOfFreeDockingSpaces(this.dockingStation.getNumberOfFreeDockingSpaces() + 1);
    }

    @Override
    public String toString() {
        return "DockingSpace{" +
                "id='" + id + '\'' +
                ", flight=" + flight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DockingSpace that = (DockingSpace) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
