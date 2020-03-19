package gr.ntua.multimediaproject.offeredservices;

public class RefuelingOfferedServiceSingleton extends OfferedService {
    private static RefuelingOfferedServiceSingleton refuelingOfferedServiceSingleton;

    private RefuelingOfferedServiceSingleton(){
        this.extraCostFactor = 0.25;
    }

    public static RefuelingOfferedServiceSingleton getInstance(){
        if(refuelingOfferedServiceSingleton == null){
            refuelingOfferedServiceSingleton = new RefuelingOfferedServiceSingleton();
        }
        return refuelingOfferedServiceSingleton;
    }

    @Override
    public String toString() {
        return "RefuelingOfferedService{" +
                "extraCostFactor=" + extraCostFactor +
                '}';
    }

}
