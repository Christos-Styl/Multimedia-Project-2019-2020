package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.flights.FlightType;
import gr.ntua.multimediaproject.flights.PlaneType;

public class ZoneADockingStation extends DockingStation {

    ZoneADockingStation(){}

    @Override
    public boolean canServiceFlightType(FlightType flightType) {
        return (flightType == FlightType.PASSENGER);
    }

    @Override
    public boolean canServicePlaneType(PlaneType planeType) {
        return (planeType == PlaneType.TURBOPROP || planeType == PlaneType.JET);
    }

}
