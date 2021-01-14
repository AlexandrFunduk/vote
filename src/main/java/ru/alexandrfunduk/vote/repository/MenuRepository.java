package ru.alexandrfunduk.vote.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Caching(
            evict = {
                    @CacheEvict(value = "menus", allEntries = true),
                    @CacheEvict(value = "menusDay", allEntries = true)
            }
    )
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        if (!menu.isNew() && get(menu.getId()) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudRepository.save(menu);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "menus", allEntries = true),
                    @CacheEvict(value = "menusDay", allEntries = true)
            }
    )

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Menu get(int id) {
        return checkNotFoundWithId(crudRepository.findById(id).orElse(null), id);
    }

    @Cacheable("menus")
    public List<Menu> getAll() {
        return crudRepository.findAll(SORT_DATE);
    }

    public List<Menu> getByRestaurant(int restaurant_id) {
        return crudRepository.getByRestaurant(restaurant_id);
    }

    @Cacheable("menusDay")
    public List<Menu> getByDay(LocalDate date) {
        return crudRepository.getByDay(date);
    }

    public Menu getByDayAndRestaurant(int restaurantId, LocalDate date) {
        List<Menu> menus = crudRepository.getByDayAndRestaurant(restaurantId, date);
        return menus.isEmpty() ? null : menus.get(0);
    }


    public List<Menu> getBetween(LocalDate startDate, LocalDate endDate) {
        return crudRepository.getBetween(startDate, endDate);
    }

    public List<Menu> getBetweenByRestaurant(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return crudRepository.getBetweenByRestaurant(startDate, endDate, restaurantId);
    }
}
