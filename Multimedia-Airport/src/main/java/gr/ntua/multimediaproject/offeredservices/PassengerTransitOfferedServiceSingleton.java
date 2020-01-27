package gr.ntua.multimediaproject.offeredservices;

public class PassengerTransitOfferedServiceSingleton extends OfferedService {
    private static PassengerTransitOfferedServiceSingleton passengerTransitOfferedServiceSingleton;

    private PassengerTransitOfferedServiceSingleton(){
        this.extraCostFactor = 0.02;
    }

    public static PassengerTransitOfferedServiceSingleton getInstance(){
        if(passengerTransitOfferedServiceSingleton == null){
            passengerTransitOfferedServiceSingleton = new PassengerTransitOfferedServiceSingleton();
        }
        return passengerTransitOfferedServiceSingleton;
    }

}
