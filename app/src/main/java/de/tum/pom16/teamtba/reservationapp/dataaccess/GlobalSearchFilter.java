package de.tum.pom16.teamtba.reservationapp.dataaccess;

import java.util.Hashtable;

/**
 * Created by evisa on 9/7/16.
 */
public class GlobalSearchFilter {
    private static GlobalSearchFilter ourInstance = new GlobalSearchFilter();

    public static GlobalSearchFilter getInstance() {
        return ourInstance;
    }

    private GlobalSearchFilter() {
    }
}
