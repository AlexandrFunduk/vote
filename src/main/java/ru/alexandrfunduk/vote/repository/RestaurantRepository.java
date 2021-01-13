package ru.alexandrfunduk.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.alexandrfunduk.vote.model.Restaurant;

import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class RestaurantRepository {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository crudRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return crudRepository.save(restaurant);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(crudRepository.findById(id).orElse(null), id);
    }

    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_NAME);
    }

    public Restaurant getByName(String name) {
        return crudRepository.getByName(name);
    }
}
