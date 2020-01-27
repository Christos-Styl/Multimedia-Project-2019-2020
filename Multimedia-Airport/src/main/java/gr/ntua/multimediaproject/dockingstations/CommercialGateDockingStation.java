package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.flights.FlightType;
import gr.ntua.multimediaproject.flights.PlaneType;
import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.util.Set;

public class CommercialGateDockingStation extends DockingStation {

    CommercialGateDockingStation(){}

    @Override
    public boolean canServiceFlightType(FlightType flightType) {
        return (flightType == FlightType.COMMERCIAL);
    }

    @Override
    public boolean canServicePlaneType(PlaneType planeType) {
        return (planeType == PlaneType.JET || planeType == PlaneType.TURBOPROP);
    }

}
