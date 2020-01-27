package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.flights.FlightType;
import gr.ntua.multimediaproject.flights.PlaneType;

public class ZoneCDockingStation extends DockingStation{

    ZoneCDockingStation(){}

    @Override
    public boolean canServiceFlightType(FlightType flightType) {
        return (flightType == FlightType.PASSENGER || flightType == FlightType.COMMERCIAL ||
                flightType == FlightType.PRIVATE);
    }

    @Override
    public boolean canServicePlaneType(PlaneType planeType) {
        return (planeType == PlaneType.SINGLE_ENGINE);
    }

}
