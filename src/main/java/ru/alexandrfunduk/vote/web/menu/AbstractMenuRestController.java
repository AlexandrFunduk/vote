package ru.alexandrfunduk.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import ru.alexandrfunduk.vote.View;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.repository.MenuRepository;
import ru.alexandrfunduk.vote.to.MenuTo;
import ru.alexandrfunduk.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.assureIdConsistent;
import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFoundWithId;

public abstract class AbstractMenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuRepository repository;

        @Autowired
        private UniqueMenuValidator menuValidator;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

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

    @Transactional
    public Menu create(MenuTo menuTo) {
        Assert.notNull(menuTo, "menu must not be null");
        Menu menu = new Menu(null, menuTo.getDay(), null, menuTo.getDishPrise());
        log.info("create {}", menu);
        return repository.save(menu, menuTo.getRestaurantId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(MenuTo menuTo, int id) throws BindException {
        Assert.notNull(menuTo, "menu must not be null");
        Menu menu = new Menu(menuTo.getId(), menuTo.getDay(), null, menuTo.getDishPrise());
        log.info("update {} with id={}", menuTo, id);
        assureIdConsistent(menu, id);
        checkNotFoundWithId(repository.save(menu, menuTo.getRestaurantId()), id);
    }

    protected void validateBeforeUpdate(MenuTo menuTo, int id) throws BindException {
        assureIdConsistent(menuTo, id);
        DataBinder binder = new DataBinder(menuTo);
        binder.addValidators(menuValidator, validator);
        binder.validate(View.Web.class);
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}
