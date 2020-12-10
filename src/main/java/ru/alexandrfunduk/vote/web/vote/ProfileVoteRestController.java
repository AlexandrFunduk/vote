package ru.alexandrfunduk.vote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.repository.VoteRepository;
import ru.alexandrfunduk.vote.util.DateTimeUtil;
import ru.alexandrfunduk.vote.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController {
    static final String REST_URL = "/rest/profile/vote";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteRepository repository;


    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll");
        return repository.getAll(SecurityUtil.authUserId());
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        return checkNotFoundWithId(repository.get(id, SecurityUtil.authUserId()), id);
    }

    @GetMapping("/today")
    public Vote get() {
        return repository.getByDay(SecurityUtil.authUserId(), LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestBody int restaurantId) {
        int userId = SecurityUtil.authUserId();
        Vote created = repository.save(new Vote(), userId, restaurantId);
        Assert.notNull(created, "vote can not be created");
        log.info("create {} for user {}", created, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id, SecurityUtil.authUserId()), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody int restaurantId, @PathVariable int id) {
        Vote vote = new Vote();
        vote.setId(id);
        int userId = SecurityUtil.authUserId();
        log.info("update {} for user {}", vote, userId);
        checkNotFoundWithId(repository.save(vote, userId, restaurantId), vote.id());

    }

    @GetMapping("/filter")
    public List<Vote> getFiltered(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam @Nullable LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam @Nullable LocalDate endDate) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates {} - {} for user {}", startDate, endDate, userId);
        return repository.getBetween(userId, DateTimeUtil.atStartOfDayOrMin(startDate), DateTimeUtil.atStartOfNextDayOrMax(endDate));
    }
}
