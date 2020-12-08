package ru.alexandrfunduk.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu menu WHERE menu.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Query("SELECT m from Menu m WHERE m.day >= :startDate AND m.day < :endDate ORDER BY m.day DESC")
    List<Menu> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Modifying
    @Query("SELECT m from Menu m WHERE m.day >= :startDate AND m.day < :endDate AND m.restaurant.id =:restaurant_id ORDER BY m.day DESC")
    List<Menu> getBetweenByRestaurant(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restaurant_id") int restaurantId);

    @Modifying
    @Query("SELECT menu FROM Menu menu WHERE menu.day=:date and menu.restaurant.id=:restaurant_id")
    Menu getDayMenuByRestaurant(@Param("restaurant_id") int restaurantId, @Param("date") LocalDate date);

    @Modifying
    @Query("SELECT menu FROM Menu menu WHERE menu.restaurant.id=:restaurant_id")
    List<Menu> getMenusByRestaurant(@Param("restaurant_id") int restaurant_Id);

    List<Menu> getMenusByDay(LocalDate date);
}
