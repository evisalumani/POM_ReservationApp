
package de.tum.pom16.teamtba.reservationapp.dataaccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.OpeningTimes;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.RestaurantReview;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 5/25/16.
 */
public class DataGenerator {
    public static List<Restaurant> generateDummyData() {
        // TODO: fill out missing elements
        List<Restaurant> allRestaurants = new ArrayList<Restaurant>();

        String r1Description = "Tantris is a restaurant in Munich, Germany. Opened in 1971, it is regarded as one of the best restaurants in Germany. It was voted 44th best in the world in the Restaurant (magazine) Top 50 2009. Chefs have included Eckart Witzigmann and Heinz Winkler. Since 1991, the chef has been Hans Haas.";
        String r4Description = "WE LOVE BURRITOS - Deshalb bringen wir den originalen \"Mission-style\" Burrito aus dem schönen San Francisco nach München! Da wir wissen, dass jeder Mensch einzigartig ist, steht alles unter dem Motto: \"Design your own Burrito\" \n" +
                "\n" +
                "In unserem Burrito stecken nur frische, regionale Zutaten - das Fleisch aus regionalem Anbau, denn Integrität ist uns genauso wichtig wie ein toller, einzigartiger Geschmack...\n" +
                "\n" +
                "Erlebe jetzt deinen \"Mission-Style\" Burrito in München! Wir freuen uns auf dich...\n" +
                "\n" +
                "Dein Burrito-Team";

        Restaurant r1 = new Restaurant("Tantris", r1Description, "Johann-Fichte-Strasse 7, 80805 München ", 41.3275, 19.8187, CuisineType.INTERNATIONAL, 40.0, 1, 9, 13, 10);
        Restaurant r2 = new Restaurant("Rabiang", "This's a Thai Resturant", "Georgenschwaigstraße 25, 80807 München", 48.137551, 11.576650, CuisineType.THAI, 25.0, 2, 12, 24, 15);
        Restaurant r3 = new Restaurant("Trattoria Da Fausto", "This's an Italian Resturant", "Helmtrudenstraße 1, 80805 München", 37.9838, 23.7275, CuisineType.ITALIAN, 30.0, 2, 8, 17, 20);
        Restaurant r4 = new Restaurant("Burrito Company", r4Description, "Augustenstraße 74, 80333 München", 48.150776, 11.564844, CuisineType.MEXICAN, 8.0, 1, 10, 20, 15);
        Restaurant r5 = new Restaurant("Les Cuisiniers", "This's a French Resturant", "Reitmorstraße 21, 80538 München", 48.143742, 11.592979, CuisineType.INTERNATIONAL, 50.0, 3, 10, 22, 10);
        Restaurant r6 = new Restaurant("Kreta Grill", "This's a Greek Resturant", "Nordendstraße 60, 80801 München", 48.162333, 11.575434, CuisineType.GREEK, 20.0, 4, 15, 23, 20);
        Restaurant r7 = new Restaurant("China Restaurant Global Wok", "This's a Chiness Resturant", "Schleißheimer Str. 106, 85748 Garching bei München", 48.250347, 11.617039, CuisineType.CHINESE, 30.0, 2, 7, 12, 20);

        allRestaurants.add(r1);
        allRestaurants.add(r2);
        allRestaurants.add(r3);
        allRestaurants.add(r4);
        allRestaurants.add(r5);
        allRestaurants.add(r6);
        allRestaurants.add(r7);

        r1.addReview(new RestaurantReview(4, "Daniel Z.", new Date(), "Ambience is nice, the staff is very friendly and the service is excellent. The food is very good as well, but there's no value for money, it is too expensive."));
        r1.addReview(new RestaurantReview(3, "Deborah J.", new Date(), "Pricey, but worth every cent."));

        r4.addReview(new RestaurantReview(4, "Lina Garcia", new Date(), "Good for a quick lunch. There is a student lunch special for €7.50, includes any burrito and a soft drink."));
        r4.addReview(new RestaurantReview(3, "Ben Howard", new Date(), "Sometimes messy - Always good. Nice, simple and very friendly burrito hole-in-the-wall. Can seat about 12 people at a time, but take-away is also an option."));

        r2.addReview(new RestaurantReview(2, "", new Date(), ""));
        r3.addReview(new RestaurantReview(4, "", new Date(), ""));
        r5.addReview(new RestaurantReview(5, "", new Date(), ""));
        r6.addReview(new RestaurantReview(1, "Michael F.", new Date(), "Das teuerste Gyros in der Geschichte Griechenlands. Wie diese Preise gerechtfertig werden, das weiss nur das Pommenbuden-Flair und die Neonlicht-Romantik, die man auf den Küchen-Holzstühlen geniessen darf."));
        r6.addReview(new RestaurantReview(1, "Michael Nyman", new Date(), "Meh"));
        r6.addReview(new RestaurantReview(2, "Michael L.", new Date(), "Average"));
        r7.addReview(new RestaurantReview(2, "", new Date(), ""));

        r1.setOpeningTimes(Calendar.MONDAY, new HourTimeSlot(10, 0), new HourTimeSlot(24, 0));
        r1.setOpeningTimes(Calendar.TUESDAY, new HourTimeSlot(10, 0), new HourTimeSlot(24, 0));
        r1.setOpeningTimes(Calendar.WEDNESDAY, new HourTimeSlot(10, 0), new HourTimeSlot(24, 0));
        r1.setOpeningTimes(Calendar.THURSDAY, new HourTimeSlot(10, 0), new HourTimeSlot(24, 0));
        r1.setOpeningTimes(Calendar.FRIDAY, new HourTimeSlot(10, 0), new HourTimeSlot(24, 0));
//        r1.setOpeningTimes(6, new HourTimeSlot(10, 0), new HourTimeSlot(24, 0));
//        r1.setOpeningTimes(7, new HourTimeSlot(10, 0), new HourTimeSlot(24, 0));

        r2.setOpeningTimes(Calendar.MONDAY, new HourTimeSlot(7, 0), new HourTimeSlot(13, 0));
        r2.setOpeningTimes(Calendar.TUESDAY, new HourTimeSlot(7, 0), new HourTimeSlot(15, 0));
        r2.setOpeningTimes(Calendar.FRIDAY, new HourTimeSlot(10, 0), new HourTimeSlot(19, 0));
        r2.setOpeningTimes(Calendar.SATURDAY, new HourTimeSlot(12, 0), new HourTimeSlot(17, 0));
        r2.setOpeningTimes(Calendar.SUNDAY, new HourTimeSlot(12, 0), new HourTimeSlot(17, 0));

        r3.setOpeningTimes(Calendar.MONDAY, new HourTimeSlot(7, 0), new HourTimeSlot(13, 0));
        r3.setOpeningTimes(Calendar.THURSDAY, new HourTimeSlot(7, 0), new HourTimeSlot(15, 0));
        r3.setOpeningTimes(Calendar.FRIDAY, new HourTimeSlot(10, 0), new HourTimeSlot(19, 0));

        r4.setOpeningTimes(Calendar.MONDAY, new HourTimeSlot(7, 0), new HourTimeSlot(13, 0));
        r4.setOpeningTimes(Calendar.TUESDAY, new HourTimeSlot(7, 0), new HourTimeSlot(15, 0));
        r4.setOpeningTimes(Calendar.FRIDAY, new HourTimeSlot(10, 0), new HourTimeSlot(19, 0));

        r5.setOpeningTimes(Calendar.MONDAY, new HourTimeSlot(7, 0), new HourTimeSlot(13, 0));
        r5.setOpeningTimes(Calendar.TUESDAY, new HourTimeSlot(7, 0), new HourTimeSlot(15, 0));
        r5.setOpeningTimes(Calendar.FRIDAY, new HourTimeSlot(10, 0), new HourTimeSlot(19, 0));

        r6.setOpeningTimes(Calendar.MONDAY, new HourTimeSlot(7, 0), new HourTimeSlot(13, 0));
        r6.setOpeningTimes(Calendar.TUESDAY, new HourTimeSlot(7, 0), new HourTimeSlot(13, 0));
        r6.setOpeningTimes(Calendar.FRIDAY, new HourTimeSlot(10, 0), new HourTimeSlot(19, 0));

        r7.setOpeningTimes(Calendar.MONDAY, new HourTimeSlot(7, 0), new HourTimeSlot(13, 0));
        r7.setOpeningTimes(Calendar.TUESDAY, new HourTimeSlot(7, 0), new HourTimeSlot(15, 0));
        r7.setOpeningTimes(Calendar.FRIDAY, new HourTimeSlot(10, 0), new HourTimeSlot(19, 0));

        Table table1 = new Table(1, 4, r1);
        Table table2 = new Table(2, 4, r2);
        Table table3 = new Table(3, 4, r3);
        Table table4 = new Table(4, 4, r4);
        Table table5 = new Table(5, 4, r5);
        Table table6 = new Table(6, 4, r6);
        Table table7 = new Table(7, 4, r7);
        Table table8 = new Table(8, 4, r2);


        r1.addTable(table1);
        r2.addTable(table2);
        r2.addTable(table8);
//        r2.addTable(table1); //test
//        r2.addTable(table3);//test
        r3.addTable(table3);
        r4.addTable(table4);
        r5.addTable(table5);
        r6.addTable(table6);
        r7.addTable(table7);

        return allRestaurants;
    }
}