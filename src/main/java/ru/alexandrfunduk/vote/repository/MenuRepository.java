package ru.alexandrfunduk.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class MenuRepository {

    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "day");

    private final CrudMenuRepository crudRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public MenuRepository(CrudMenuRepository crudRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        if (!menu.isNew() && get(menu.getId()) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudRepository.save(menu);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Menu get(int id) {
        return checkNotFoundWithId(crudRepository.findById(id).orElse(null), id);
    }

    public List<Menu> getAll() {
        return crudRepository.findAll(SORT_DATE);
    }

    public List<Menu> getByRestaurant(int restaurant_id) {
        return crudRepository.getMenusByRestaurant(restaurant_id);
    }

    public List<Menu> getByDay(LocalDate date) {
        return crudRepository.getMenusByDay(date);
    }

    public List<Menu> getBetween(LocalDate startDate, LocalDate endDate) {
        return crudRepository.getBetween(startDate, endDate);
    }

    public List<Menu> getBetweenByRestaurant(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return crudRepository.getBetweenByRestaurant(startDate, endDate, restaurantId);
    }
}
