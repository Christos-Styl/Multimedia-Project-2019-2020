package gr.ntua.multimediaproject.offeredservices;

public class LoadingUnloadingOfferedServiceSingleton extends OfferedService {
    private static LoadingUnloadingOfferedServiceSingleton loadingUnloadingOfferedServiceSingleton;

    private LoadingUnloadingOfferedServiceSingleton(){
        this.extraCostFactor = 0.05;
    }

    public static LoadingUnloadingOfferedServiceSingleton getInstance(){
        if(loadingUnloadingOfferedServiceSingleton == null){
            loadingUnloadingOfferedServiceSingleton = new LoadingUnloadingOfferedServiceSingleton();
        }
        return loadingUnloadingOfferedServiceSingleton;
    }

    @Override
    public String toString() {
        return "LoadingUnloadingOfferedService{" +
                "extraCostFactor=" + extraCostFactor +
                '}';
    }
}
