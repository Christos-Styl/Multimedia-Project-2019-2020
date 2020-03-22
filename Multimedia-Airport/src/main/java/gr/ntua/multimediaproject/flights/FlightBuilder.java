package gr.ntua.multimediaproject.flights;

import gr.ntua.multimediaproject.airport.AirportSingleton;
import gr.ntua.multimediaproject.flights.exceptions.FlightException;
import gr.ntua.multimediaproject.offeredservices.OfferedService;

import java.util.Random;
import java.util.Set;

public class FlightBuilder {
    private static Random random = new Random(System.currentTimeMillis());

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

    public FlightBuilder ofPredictedParkingDurationInMinutes(int predictedTakeoffTimeInMinutes) throws FlightException {
        if(predictedTakeoffTimeInMinutes <= 0){
            throw new FlightException("predictedParkingDurationInMinutes cannot be non-positive.");
        }
        flight.setPredictedParkingDurationInMinutes(predictedTakeoffTimeInMinutes);
        return this;
    }

    public FlightBuilder ofInitialCommunicationTime(int initialCommunicationTime) throws FlightException {
        if(initialCommunicationTime < 0){
            throw new FlightException("initialCommunicationTime cannot be negative.");
        }
        flight.setInitialCommunicationTime(initialCommunicationTime);
        return this;
    }

    public FlightBuilder ofRequestedOfferedServicesSet(Set<OfferedService> requestedOfferedServicesSet){
        flight.setRequestedOfferedServicesSet(requestedOfferedServicesSet);
        return this;
    }

    public FlightBuilder ofAirport(AirportSingleton airport){
        flight.setAirport(airport);
        return this;
    }

    public boolean areFlightRequiredFieldsOK(){
        return (flight.getId() != null &&
                flight.getCity() != null &&
                flight.getFlightState() != null &&
                flight.getFlightType() != null &&
                flight.getPlaneType() != null &&
                flight.getPredictedParkingDurationInMinutes() > 0 &&
                flight.getInitialCommunicationTime() >= 0 &&
                flight.getTimeNeededToDockInMinutes() > 0 &&
                flight.getRequestedOfferedServicesSet() != null) &&
                flight.getDockingSpace() == null &&
                flight.getAirport() != null;
    }

    public Flight build() throws FlightException{
        if(!areFlightRequiredFieldsOK()){
            throw new FlightException("Flight does not have all required fields.");
        }
        int predictedTakeoffTimeInMinutes = flight.getPredictedParkingDurationInMinutes();
        int randomDelayTimeInMinutes = createRandomDelayBasedOnPredictedTakeoff(predictedTakeoffTimeInMinutes);
        this.flight.setDelayTimeInMinutes(randomDelayTimeInMinutes);
        this.flight.setDockingSpace(null);
        return this.flight;
    }

    private int createRandomDelayBasedOnPredictedTakeoff(int predictedTakeoffTimeInMinutes){
        int totalTimeInDockingSpace = flight.getPredictedParkingDurationInMinutes() + flight.getTimeNeededToDockInMinutes();
        if(flight.getPredictedParkingDurationInMinutes() < 2){
            return 0;
        }
        int soonerLaterOrOnTime = random.ints(1, 101).findFirst().getAsInt();
        int delayTimeInMinutes;

        //a flight will be able to depart sooner only if totalTimeInDockingSpace is sufficiently high
        if(soonerLaterOrOnTime <= 25 && totalTimeInDockingSpace >= 15){     //flight will depart sooner than predicted - negative delay
            delayTimeInMinutes = random.ints(-flight.getPredictedParkingDurationInMinutes()/2, 0)
                    .findFirst().getAsInt();
        }
        else if(soonerLaterOrOnTime <= 50){      //flight will depart later than predicted
            delayTimeInMinutes = random.ints(0, flight.getPredictedParkingDurationInMinutes()/2)
                    .findFirst().getAsInt();
        }
        else{                                   //flight will depart on time
            delayTimeInMinutes = 0;
        }
        return delayTimeInMinutes;
    }
}
