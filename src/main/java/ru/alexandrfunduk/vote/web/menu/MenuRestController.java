package ru.alexandrfunduk.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.repository.MenuRepository;

import java.net.URI;
import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    static final String REST_URL = "/rest/menu";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuRepository repository;

    @GetMapping
    public List<Menu> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@RequestBody Menu menu) {
        log.info("create {}", menu);
        Assert.notNull(menu, "user must not be null");
        checkNew(menu);
        Menu created = repository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable int id) {
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        Assert.notNull(menu, "user must not be null");
        checkNotFoundWithId(repository.save(menu), menu.id());
    }
}
