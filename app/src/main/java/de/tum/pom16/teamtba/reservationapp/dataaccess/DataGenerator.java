
package de.tum.pom16.teamtba.reservationapp.dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.RestaurantReview;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

/**
 * Created by evisa on 5/25/16.
 */
public class DataGenerator {
    public static List<Restaurant> generateDummyData() {
        // TODO: fill out missing elements (description, address)
        List<Restaurant> allRestaurants = new ArrayList<Restaurant>();

        String r1Description = "Tantris is a restaurant in Munich, Germany. Opened in 1971, it is regarded as one of the best restaurants in Germany. It was voted 44th best in the world in the Restaurant (magazine) Top 50 2009. Chefs have included Eckart Witzigmann and Heinz Winkler. Since 1991, the chef has been Hans Haas.";
        String r4Description = "WE LOVE BURRITOS - Deshalb bringen wir den originalen \"Mission-style\" Burrito aus dem schönen San Francisco nach München! Da wir wissen, dass jeder Mensch einzigartig ist, steht alles unter dem Motto: \"Design your own Burrito\" \n" +
                "\n" +
                "In unserem Burrito stecken nur frische, regionale Zutaten - das Fleisch aus regionalem Anbau, denn Integrität ist uns genauso wichtig wie ein toller, einzigartiger Geschmack...\n" +
                "\n" +
                "Erlebe jetzt deinen \"Mission-Style\" Burrito in München! Wir freuen uns auf dich...\n" +
                "\n" +
                "Dein Burrito-Team";

        Restaurant r1 = new Restaurant("Tantris", r1Description, "Johann-Fichte-Strasse 7, 80805 München ", 48.137107, 11.575373, CuisineType.INTERNATIONAL, 40.0, 9, 13, 10);
        Restaurant r2 = new Restaurant("Rabiang", "This's a Thai Resturant", "Georgenschwaigstraße 25, 80807 München", 48.137551, 11.576650, CuisineType.THAI, 25.0, 12, 24, 15);
        Restaurant r3 = new Restaurant("Trattoria Da Fausto", "This's an Italian Resturant", "Helmtrudenstraße 1, 80805 München", 48.138295, 11.575212, CuisineType.ITALIAN, 30.0, 8, 17, 20);
        Restaurant r4 = new Restaurant("Burrito Company", r4Description, "Augustenstraße 74, 80333 München", 48.150776, 11.564844, CuisineType.MEXICAN, 8.0, 10, 20, 15);
        Restaurant r5 = new Restaurant("Les Cuisiniers", "This's a French Resturant", "Reitmorstraße 21, 80538 München", 48.143742, 11.592979, CuisineType.FRENCH, 50.0, 10, 22, 10);
        Restaurant r6 = new Restaurant("Kreta Grill", "This's a Greek Resturant", "Nordendstraße 60, 80801 München", 48.162333, 11.575434, CuisineType.GREEK, 20.0, 15, 23, 20);
        Restaurant r7 = new Restaurant("China Restaurant Global Wok", "This's a Chiness Resturant", "Schleißheimer Str. 106, 85748 Garching bei München", 48.250347, 11.617039, CuisineType.CHINESE, 30.0, 7, 12, 20);

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
        r7.addReview(new RestaurantReview(2, "", new Date(), ""));

        return allRestaurants;
    }
}