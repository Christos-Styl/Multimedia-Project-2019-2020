package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.flights.FlightType;
import gr.ntua.multimediaproject.flights.PlaneType;

public class GeneralDockingStation extends DockingStation{

    GeneralDockingStation(){}

    @Override
    public boolean canServiceFlightType(FlightType flightType) {
        return (flightType == FlightType.PASSENGER || flightType == FlightType.COMMERCIAL ||
                flightType == FlightType.PRIVATE);
    }

    @Override
    public boolean canServicePlaneType(PlaneType planeType) {
        return (planeType == PlaneType.TURBOPROP || planeType == PlaneType.SINGLE_ENGINE ||
                planeType == PlaneType.JET);
    }
}
