package ru.alexandrfunduk.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.repository.MenuRepository;
import ru.alexandrfunduk.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.*;

public abstract class AbstractMenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuRepository repository;

    public List<Menu> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public Menu get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Menu> getByToday() {
        return repository.getByDay(LocalDate.now());
    }

    public List<Menu> getFiltered(@Nullable LocalDate startDate, @Nullable LocalDate endDate, @Nullable Integer restaurantId) {
        log.info("getBetween dates {} - {} for restaurant id {}", startDate, endDate, restaurantId);
        return restaurantId == null ?
                repository.getBetween(DateTimeUtil.atStartOfDayOrMin(startDate), DateTimeUtil.atStartOfNextDayOrMax(endDate))
                : repository.getBetweenByRestaurant(DateTimeUtil.atStartOfDayOrMin(startDate), DateTimeUtil.atStartOfNextDayOrMax(endDate), restaurantId);
    }

    public Menu create(Menu menu, Integer restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        checkNew(menu);
        log.info("create {}", menu);
        return repository.save(menu, restaurantId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public void update(Menu menu, int id, int restaurantId) {
        log.info("update {} with id={}", menu, id);
        Assert.notNull(menu, "menu must not be null");
        assureIdConsistent(menu, id);
        checkNotFoundWithId(repository.save(menu, restaurantId), id);
    }
}
