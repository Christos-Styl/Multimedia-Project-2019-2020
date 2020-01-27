package gr.ntua.multimediaproject.commonutils;

import java.util.Collection;

public class AbstractChecker {
    public static boolean canLoopCollection(Collection col){
        return (col != null && col.size() != 0);
    }
}
