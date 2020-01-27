package gr.ntua.multimediaproject.flights;

import gr.ntua.multimediaproject.flights.exceptions.FlightException;
import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.util.Set;

public class FlightBuilder {
    private Flight flight = new Flight();

    public static FlightBuilder create(){
        return new FlightBuilder();
    }

    public FlightBuilder ofId(String id){
        flight.setId(id);
        return this;
    }

    public FlightBuilder ofCity(String city){
        flight.setCity(city);
        return this;
    }

    public FlightBuilder ofPlaneType(PlaneType planeType) throws FlightException{
        switch(planeType) {
            case JET:
                flight.setPlaneType(PlaneType.JET);
                flight.setTimeNeededToDockInMinutes(2);
                break;
            case TURBOPROP:
                flight.setPlaneType(PlaneType.TURBOPROP);
                flight.setTimeNeededToDockInMinutes(4);
                break;
            case SINGLE_ENGINE:
                flight.setPlaneType(PlaneType.SINGLE_ENGINE);
                flight.setTimeNeededToDockInMinutes(6);
                break;
            default:
                throw new FlightException("No proper PlaneType was specified.");
        }
        return this;
    }

    public FlightBuilder ofFlightType(FlightType flightType){
        flight.setFlightType(flightType);
        return this;
    }

    public FlightBuilder ofFlightState(FlightState flightState){
        flight.setFlightState(flightState);
        return this;
    }

    public FlightBuilder ofPredictedTakeoffTimeInMinutes(int predictedTakeoffTimeInMinutes) throws FlightException {
        if(predictedTakeoffTimeInMinutes <= 0){
            throw new FlightException("PredictedTakeoffTimeInMinutes cannot be non-positive.");
        }
        flight.setPredictedTakeoffTimeInMinutes(predictedTakeoffTimeInMinutes);
        return this;
    }

    public FlightBuilder ofRequestedOfferedServicesSet(Set<OfferedService> requestedOfferedServicesSet){
        flight.setRequestedOfferedServicesSet(requestedOfferedServicesSet);
        return this;
    }

    public boolean doesFlightHaveRequiredFields(){
        return (flight.getId() != null &&
                flight.getCity() != null &&
                flight.getFlightState() != null &&
                flight.getFlightType() != null &&
                flight.getPlaneType() != null &&
                flight.getPredictedTakeoffTimeInMinutes() != 0 &&
                flight.getTimeNeededToDockInMinutes() != 0 &&
                flight.getRequestedOfferedServicesSet() != null);
    }

    public Flight build() throws FlightException{
        if(!doesFlightHaveRequiredFields()){
            throw new FlightException("Flight does not have all required fields.");
        }
        if(flight.getTimeNeededToDockInMinutes() >= flight.getPredictedTakeoffTimeInMinutes()){
            throw new FlightException("TimeNeededToDockInMinutes must be less than predictedTakeoffTimeInMinutes");
        }
        return this.flight;
    }
}
