package ru.alexandrfunduk.vote.web.restaurant;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import ru.alexandrfunduk.vote.model.Restaurant;
import ru.alexandrfunduk.vote.repository.RestaurantRepository;
import ru.alexandrfunduk.vote.web.ExceptionInfoHandler;

@Component
public class UniqueRestaurantValidator implements org.springframework.validation.Validator {
    private final RestaurantRepository repository;

    public UniqueRestaurantValidator(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = (Restaurant) target;
        if (StringUtils.hasText(restaurant.getName())) {
            Restaurant dbRestaurant = repository.getByName(restaurant.getName().toLowerCase());
            if (dbRestaurant != null && !dbRestaurant.getId().equals(restaurant.getId())) {
                errors.rejectValue("name", ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT);
            }
        }
    }
}
