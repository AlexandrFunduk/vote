package ru.alexandrfunduk.vote;

import ru.alexandrfunduk.vote.model.Restaurant;

import java.util.Calendar;
import java.util.Date;
import java.util.Date;
import java.util.GregorianCalendar;

import static ru.alexandrfunduk.vote.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class,"registered");
    public static final int RESTAURANT_ID = START_SEQ+4;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID,"restaurant1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID+1,"restaurant2");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID+2,"restaurant3");


    public static Restaurant getNew() {
        return new Restaurant(null,"NewRestaurant");
    }
}
