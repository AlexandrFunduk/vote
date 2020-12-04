package ru.alexandrfunduk.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu menu WHERE menu.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Query("SELECT menu FROM Menu menu WHERE menu.day=:date and menu.restaurant=:restaurant")
    Menu getDayMenuByRestaurant(@Param("restaurant") Restaurant restaurant, @Param("date") LocalDate date);

    List<Menu> getMenusByRestaurant(Restaurant restaurant);

    List<Menu> getMenusByDay(LocalDate date);
}
