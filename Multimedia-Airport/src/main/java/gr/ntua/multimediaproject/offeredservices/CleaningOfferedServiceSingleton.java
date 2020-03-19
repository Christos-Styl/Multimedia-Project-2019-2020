package gr.ntua.multimediaproject.offeredservices;

public class CleaningOfferedServiceSingleton extends OfferedService {
    private static CleaningOfferedServiceSingleton cleaningOfferedServiceSingleton;

    private CleaningOfferedServiceSingleton(){
        this.extraCostFactor = 0.02;
    }

    public static CleaningOfferedServiceSingleton getInstance(){
        if(cleaningOfferedServiceSingleton == null){
            cleaningOfferedServiceSingleton = new CleaningOfferedServiceSingleton();
        }
        return cleaningOfferedServiceSingleton;
    }

    @Override
    public String toString() {
        return "CleaningOfferedService{" +
                "extraCostFactor=" + extraCostFactor +
                '}';
    }
}
