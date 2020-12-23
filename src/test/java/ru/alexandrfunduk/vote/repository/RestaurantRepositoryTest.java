package ru.alexandrfunduk.vote.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.alexandrfunduk.vote.TimingExtension;
import ru.alexandrfunduk.vote.model.Restaurant;
import ru.alexandrfunduk.vote.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alexandrfunduk.vote.RestaurantTestData.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    void save() {
        Restaurant created = repository.save(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.get(newId), newRestaurant);
    }

    @Test
    void delete() {
        repository.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> repository.get(RESTAURANT_ID));
    }

    @Test
    void get() {
        Restaurant restaurant = repository.get(RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void getAll() {
        List<Restaurant> all = repository.getAll();
        RESTAURANT_MATCHER.assertMatch(all, restaurant1, restaurant2, restaurant3);
    }

    @Test
    void getByName() {
        Restaurant restaurant = repository.getByName("restaurant1");
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
    }
}