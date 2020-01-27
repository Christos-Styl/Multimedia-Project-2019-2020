package gr.ntua.multimediaproject.offeredservices;

import java.util.Objects;

public abstract class OfferedService {
    protected double extraCostFactor;

    OfferedService(){}

    public double getExtraCostFactor() {
        return extraCostFactor;
    }

    public void setExtraCostFactor(double extraCostFactor) {
        this.extraCostFactor = extraCostFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferedService that = (OfferedService) o;
        return Double.compare(that.extraCostFactor, extraCostFactor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(extraCostFactor);
    }

    @Override
    public String toString() {
        return "OfferedService{" +
                "type=" + this.getClass().toString() +
                "extraCostFactor=" + extraCostFactor +
                '}';
    }
}
