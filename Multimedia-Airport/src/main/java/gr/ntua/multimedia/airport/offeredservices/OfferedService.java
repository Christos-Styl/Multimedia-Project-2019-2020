package gr.ntua.multimedia.airport.offeredservices;

public abstract class OfferedService {
    protected double extraCostFactor;

    OfferedService(){}

    public double getExtraCostFactor() {
        return extraCostFactor;
    }

    public void setExtraCostFactor(double extraCostFactor) {
        this.extraCostFactor = extraCostFactor;
    }
}
