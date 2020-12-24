package ru.alexandrfunduk.vote;

import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.to.MenuTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static ru.alexandrfunduk.vote.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class,"restaurant");
    public static final int MENU_ID = START_SEQ + 7;

    public static final Menu menu1 = new Menu(MENU_ID, LocalDate.of(2020, 12, 1), RestaurantTestData.restaurant1, Map.of("borsch",10000,"pie", 20000, "tea",5000));
    public static final Menu menu2 = new Menu(MENU_ID+1, LocalDate.of(2020, 12, 1), RestaurantTestData.restaurant2, Map.of("pizza",27000,"pasta", 25000, "coffee",11000));
    public static final Menu menu3 = new Menu(MENU_ID+2, LocalDate.of(2020, 12, 1), RestaurantTestData.restaurant3, Map.of("roast",30000,"salad", 27000, "juice",8000));
    public static final Menu menu4 = new Menu(MENU_ID+3, LocalDate.of(2020, 12, 2), RestaurantTestData.restaurant1, Map.of("borsch1",10100,"pie1", 20100, "tea1",5100));
    public static final Menu menu5 = new Menu(MENU_ID+4, LocalDate.of(2020, 12, 2), RestaurantTestData.restaurant2, Map.of("pizza1",27100,"pasta1", 25100, "coffee1",11100));
    public static final Menu menu6 = new Menu(MENU_ID+5, LocalDate.of(2020, 12, 2), RestaurantTestData.restaurant3, Map.of("roast1",30100,"salad1", 27100, "juice1",8010));
    public static final Menu menu7 = new Menu(MENU_ID+6, LocalDate.now(), RestaurantTestData.restaurant1, Map.of("roast2",333300,"salad2", 200000, "juice2",1000));

    public static final MenuTo menuTo2 = new MenuTo(MENU_ID+1, LocalDate.of(2020, 12, 1), RestaurantTestData.restaurant2.getId(), Map.of("pizza",27000,"pasta", 25000, "coffee",11000)); // todo

    public static final List<Menu> menus = List.of(menu7, menu6, menu5, menu4, menu3, menu2, menu1);
    public static final List<Menu> menusDay = List.of(menu7);

    public static Menu getNew() {
        return new Menu(null, LocalDate.of(2020, 12, 3), RestaurantTestData.restaurant1, Map.of("A",10,"B", 20));
    }

    public static MenuTo getNewTo() {
        return new MenuTo(null, LocalDate.of(2020, 12, 3), RestaurantTestData.restaurant1.getId(), Map.of("A",10,"B", 20));

    }

    public static Menu getUpdated() {
        return new Menu(MENU_ID, LocalDate.of(2020, 12, 3), RestaurantTestData.restaurant2, Map.of("D",30,"E", 40));

    }

    public static MenuTo getUpdatedTo() {
        return new MenuTo(MENU_ID, LocalDate.of(2020, 12, 3), RestaurantTestData.restaurant2.getId(), Map.of("D",30,"E", 40));

    }

}
