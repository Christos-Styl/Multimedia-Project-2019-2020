package gr.ntua.multimediaproject.flights;

import gr.ntua.multimediaproject.flights.exceptions.FlightException;

public enum PlaneType {
    JET,
    TURBOPROP,
    SINGLE_ENGINE;

    public static PlaneType valueOf(int i) throws FlightException {
        switch(i){
            case 1:
                return PlaneType.SINGLE_ENGINE;
            case 2:
                return PlaneType.TURBOPROP;
            case 3:
                return PlaneType.JET;
            default:
                throw new FlightException("The integer provided as Plane Type does not map to any " +
                        "PlaneType. Currently supported values are 1-3.");
        }
    }
}
