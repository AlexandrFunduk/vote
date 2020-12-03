package ru.alexandrfunduk.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.model.Restaurant;

import java.util.List;

@Repository
public class MenuRepository {

    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "day");

    private final CrudMenuRepository crudRepository;

    public MenuRepository(CrudMenuRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Menu save(Menu menu) {
        return crudRepository.save(menu);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Menu get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Menu> getAll() {
        return crudRepository.findAll(SORT_DATE);
    }
}
