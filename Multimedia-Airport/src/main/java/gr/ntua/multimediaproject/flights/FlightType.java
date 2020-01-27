package gr.ntua.multimediaproject.flights;

import gr.ntua.multimediaproject.flights.exceptions.FlightException;

public enum FlightType {
    PASSENGER,
    COMMERCIAL,
    PRIVATE;

    public static FlightType valueOf(int i) throws FlightException {
        switch(i){
            case 1:
                return FlightType.PASSENGER;
            case 2:
                return FlightType.COMMERCIAL;
            case 3:
                return FlightType.PRIVATE;
            default:
                throw new FlightException("The integer provided as Flight Type does not map to any " +
                        "FlightType. Currently supported values are 1-3.");
        }
    }
}
