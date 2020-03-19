package gr.ntua.multimediaproject.commonutils;

import java.util.Collection;

public abstract class AbstractHelper {

    public static boolean canLoopCollection(Collection col){
        return (col != null && col.size() != 0);
    }

    public static int getRealMillisecondsForOneSimulationMinute() {
        return 10;
    }

}
