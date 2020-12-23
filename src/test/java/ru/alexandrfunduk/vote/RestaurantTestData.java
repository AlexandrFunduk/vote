package ru.alexandrfunduk.vote;

import ru.alexandrfunduk.vote.model.Restaurant;

import java.util.Date;
import java.util.GregorianCalendar;

import static ru.alexandrfunduk.vote.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);
    public static final int RESTAURANT_ID = START_SEQ+4;
    public static final Date DATE = new GregorianCalendar(2020,12,1).getTime();

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID,"restaurant1", DATE);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID+1,"restaurant2", DATE);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID+2,"restaurant3", DATE);


    public static Restaurant getNew() {
        return new Restaurant(null,"NewRestaurant", DATE);
    }
}
