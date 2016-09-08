package de.tum.pom16.teamtba.reservationapp.models;

/**
 * Created by evisa on 9/6/16.
 */
public class OpeningTimes {
    private int openingHour;
    private int closingHour;

    public OpeningTimes(int openingHour, int closingHour) {
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public int getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(int openingHour) {
        this.openingHour = openingHour;
    }

    public int getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(int closingHour) {
        this.closingHour = closingHour;
    }
}

