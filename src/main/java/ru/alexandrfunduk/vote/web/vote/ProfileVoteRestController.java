package ru.alexandrfunduk.vote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexandrfunduk.vote.AuthorizedUser;
import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.repository.VoteRepository;
import ru.alexandrfunduk.vote.util.DateTimeUtil;

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
    public List<Vote> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("getAll");
        return repository.getAll(authUser.getId());
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        return checkNotFoundWithId(repository.get(id, authUser.getId()), id);
    }

    @GetMapping("/today")
    public Vote get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return repository.getByDay(authUser.getId(), LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestBody int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
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
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id, authUser.getId()), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody int restaurantId, @PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        Vote vote = new Vote();
        vote.setId(id);
        int userId = authUser.getId();
        log.info("update {} for user {}", vote, userId);
        checkNotFoundWithId(repository.save(vote, userId, restaurantId), vote.id());
    }

    @GetMapping("/filter")
    public List<Vote> getFiltered(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam @Nullable LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam @Nullable LocalDate endDate,
            @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("getBetween dates {} - {} for user {}", startDate, endDate, userId);
        return repository.getBetween(userId, DateTimeUtil.atStartOfDayOrMin(startDate), DateTimeUtil.atStartOfNextDayOrMax(endDate));
    }
}
