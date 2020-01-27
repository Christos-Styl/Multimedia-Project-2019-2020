package gr.ntua.multimediaproject.dockingstations;

import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;

public enum DockingStationType {
    GATE,
    COMMERCIAL_GATE,
    ZONE_A,
    ZONE_B,
    ZONE_C,
    GENERAL_DOCKING_STATION,
    LONG_DURATION;

    public static DockingStationType valueOf(int i) throws DockingStationException {
        switch(i){
            case 1:
                return DockingStationType.GATE;
            case 2:
                return DockingStationType.COMMERCIAL_GATE;
            case 3:
                return DockingStationType.ZONE_A;
            case 4:
                return DockingStationType.ZONE_B;
            case 5:
                return DockingStationType.ZONE_C;
            case 6:
                return DockingStationType.GENERAL_DOCKING_STATION;
            case 7:
                return DockingStationType.LONG_DURATION;
            default:
                throw new DockingStationException("The integer provided as Docking Station Type does not map to any " +
                        "DockingStationType. Currently supported values are 1-7.");
        }
    }
}
