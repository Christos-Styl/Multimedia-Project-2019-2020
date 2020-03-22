package gr.ntua.multimediaproject.commonutils;

import java.util.Collection;

public abstract class AbstractHelper {

    public static boolean canLoopCollection(Collection col){
        return (col != null && col.size() != 0);
    }

    public static int getRealMillisecondsForOneSimulationMinute() {
        return 200;
    }

    public static String minutesToHoursMinutes(int minutes){
        int hh = minutes / 60;
        int mm = minutes % 60;
        return String.format("%d:%02d", hh, mm);
    }

}
