package de.tum.pom16.teamtba.reservationapp.models;

/**
 * Created by evisa on 10/8/16.
 */
public class UserContact {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public UserContact(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
