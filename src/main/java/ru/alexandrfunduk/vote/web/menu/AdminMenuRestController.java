package ru.alexandrfunduk.vote.web.menu;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.to.MenuTo;
import ru.alexandrfunduk.vote.util.exception.ApplicationException;
import ru.alexandrfunduk.vote.util.exception.ErrorType;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractMenuRestController {
    static final String REST_URL = "/rest/admin/menu";

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateWithValidation(@Valid @RequestBody MenuTo menuTo, @PathVariable int id) throws BindException {
        validateBeforeUpdate(menuTo, id);
        Menu updated = super.update(menuTo, id);
        if (updated.getId() == null) {
            throw new ApplicationException("exception.restaurantNotFound", ErrorType.DATA_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody MenuTo menuTo) throws BindException {
        validateBefore(menuTo);
        Menu created = super.create(menuTo);
        if (created.getRestaurant() == null) {
            throw new ApplicationException("exception.restaurantNotFound", ErrorType.DATA_ERROR);
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
