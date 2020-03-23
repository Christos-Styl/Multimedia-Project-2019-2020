package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;
import gr.ntua.multimediaproject.flights.Flight;

import java.util.Objects;

public class DockingSpace {
    private String id;
    private Flight flight;
    private DockingStation dockingStation;

    /**
     * Creates a new DockingSpace
     */
    public DockingSpace() {}

    /**
     * Gets the DockingSpace id
     * @return this DockingSpace's id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the DockingSpace's id
     * @param id DockingSpace's new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the DockingSpace's associated Flight
     * @return this DockingSpace's Flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the DockingSpace's Flight
     * @param flight DockingSpace's new Flight
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Gets the DockingStation that the DockingSpace belongs to
     * @return this DockingSpace's DockingStation
     */
    public DockingStation getDockingStation() {
        return dockingStation;
    }

    /**
     * Sets the DockingStation that the DockingSpace belongs to
     * @param dockingStation DockingSpace's new DockingStation
     */
    public void setDockingStation(DockingStation dockingStation) {
        this.dockingStation = dockingStation;
    }

    /**
     * After DockingSpace's associated Flight takes off, the DockingSpace's corresponding DockingStation
     * has its free spaces increased by 1
     * @throws DockingStationException in case the corresponding DockingStation is already at max free
     * spaces (signifying a logical error)
     */
    public synchronized void updateDockingStationAfterTakeoff() throws DockingStationException{
        if(dockingStation.getNumberOfFreeDockingSpaces() + 1 > dockingStation.getDockingSpaceList().size()){
            throw new DockingStationException("Tried to free docking space in Docking Station with docking space : \""
                    + this.id + "\" but Docking Station should already be completely empty...");
        }
        this.dockingStation.setNumberOfFreeDockingSpaces(this.dockingStation.getNumberOfFreeDockingSpaces() + 1);
    }

    /**
     * Overloaded toString() function used to create a String representation of the DockingSpace
     * @return the String representation of the DockingSpace
     */
    @Override
    public String toString() {
        return "DockingSpace{" +
                "id='" + id + '\'' +
                ", flight=" + flight +
                '}';
    }

    /**
     * Overloaded equals() function used to compare two DockingSpaces for equality by comparing their ids
     * @return true if the two DockingSpaces are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DockingSpace that = (DockingSpace) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Overloaded hashCode() function used in case a reliable hash of DockingSpaces is required. Uses ids for hashing
     * @return hash of the DockingSpace
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
