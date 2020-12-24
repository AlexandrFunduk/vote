package ru.alexandrfunduk.vote.web.menu;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.repository.MenuRepository;
import ru.alexandrfunduk.vote.to.MenuTo;
import ru.alexandrfunduk.vote.web.ExceptionInfoHandler;

import java.time.LocalDate;

@Component
public class UniqueMenuValidator implements org.springframework.validation.Validator {
    private final MenuRepository repository;

    public UniqueMenuValidator(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MenuTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MenuTo menuTo = (MenuTo) target;
        Integer restaurantId = menuTo.getRestaurantId();
        LocalDate date = menuTo.getDay();
        if (date != null && restaurantId != null) {
            Menu dbMenu = repository.getByDayAndRestaurant(restaurantId, date);
            if (dbMenu != null && !dbMenu.getRestaurant().getId().equals(restaurantId)) {
                errors.rejectValue("day" ,ExceptionInfoHandler.EXCEPTION_DUPLICATE_MENU_TODAY);
            }
        }
    }
}
