package ru.alexandrfunduk.vote.web.menu;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.alexandrfunduk.vote.model.Menu;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController extends AbstractMenuRestController {
    static final String REST_URL = "/rest/menus";

    @Override
    @GetMapping
    public List<Menu> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/today")
    public List<Menu> getByDay() {
        return super.getByToday();
    }

    @Override
    @GetMapping("/filter")
    public List<Menu> getFiltered(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam @Nullable LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam @Nullable LocalDate endDate,
            @RequestParam @Nullable Integer restaurantId) {
        return super.getFiltered(startDate, endDate, restaurantId);
    }
}
