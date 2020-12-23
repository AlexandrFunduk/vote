package ru.alexandrfunduk.vote.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.alexandrfunduk.vote.RestaurantTestData;
import ru.alexandrfunduk.vote.TimingExtension;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alexandrfunduk.vote.MenuTestData.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
class MenuRepositoryTest {

    @Autowired
    private MenuRepository repository;

    @Test
    void save() {
        Menu created = repository.save(getNew(), RestaurantTestData.RESTAURANT_ID);
        int newId = created.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(repository.get(newId), newMenu);
    }

    @Test
    void delete() {
        repository.delete(MENU_ID);
        assertThrows(NotFoundException.class, () -> repository.get(MENU_ID));
    }

    @Test
    void get() {
        Menu menu = repository.get(MENU_ID);
        MENU_MATCHER.assertMatch(menu, menu1);
    }

    @Test
    void getAll() {
        List<Menu> all = repository.getAll();
        MENU_MATCHER.assertMatch(all, menu7, menu6, menu5, menu4, menu3, menu2, menu1);
    }

    @Test
    void getByRestaurant() {
        List<Menu> menus = repository.getByRestaurant(RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(menus, menu7, menu4, menu1);
    }

    @Test
    void getByDay() {
        List<Menu> menus = repository.getByDay(LocalDate.of(2020, 12, 1));
        MENU_MATCHER.assertMatch(menus, menu1, menu2, menu3);
    }

    @Test
    void getBetween() {
        List<Menu> menus = repository.getBetween(LocalDate.of(2020, 12, 2), LocalDate.now());
        MENU_MATCHER.assertMatch(menus, menu6, menu5, menu4);
    }

    @Test
    void getBetweenByRestaurant() {
        List<Menu> menus = repository.getBetweenByRestaurant(LocalDate.of(2020, 12, 1), LocalDate.of(2020, 12, 2), RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(menus, menu1);
    }
}