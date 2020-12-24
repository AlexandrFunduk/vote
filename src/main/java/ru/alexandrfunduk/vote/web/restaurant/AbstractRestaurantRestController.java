package ru.alexandrfunduk.vote.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.alexandrfunduk.vote.View;
import ru.alexandrfunduk.vote.model.Restaurant;
import ru.alexandrfunduk.vote.repository.RestaurantRepository;

import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.assureIdConsistent;
import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFoundWithId;

public abstract class AbstractRestaurantRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private UniqueRestaurantValidator restaurantValidator;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(restaurantValidator);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public void update(Restaurant restaurant, int id) throws BindException {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    protected void validateBeforeUpdate(Restaurant restaurant, int id) throws BindException {
        assureIdConsistent(restaurant, id);
        DataBinder binder = new DataBinder(restaurant);
        binder.addValidators(restaurantValidator, validator);
        binder.validate(View.Web.class);
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}
