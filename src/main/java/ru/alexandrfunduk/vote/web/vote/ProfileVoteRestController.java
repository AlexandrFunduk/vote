package ru.alexandrfunduk.vote.web.vote;

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
import ru.alexandrfunduk.vote.to.VoteTo;
import ru.alexandrfunduk.vote.util.DateTimeUtil;
import ru.alexandrfunduk.vote.util.exception.ApplicationException;
import ru.alexandrfunduk.vote.util.exception.ErrorType;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController extends AbstractVoteController {
    static final String REST_URL = "/rest/profile/votes";

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("getAll");
        return repository.getAll(authUser.getId());
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        return checkNotFoundWithId(repository.get(id, authUser.getId()), id);
    }

    @GetMapping("/today")
    public VoteTo get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return repository.getByDay(LocalDate.now(), authUser.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@RequestBody int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        VoteTo created = repository.save(new Vote(), userId, restaurantId);
        Assert.notNull(created, "vote can not be created");
        if (created.getId() == null) {
            throw new ApplicationException("exception.restaurantNotFound", ErrorType.DATA_ERROR);
        }
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
    public void update(@RequestBody Integer restaurantId, @PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        Vote vote = new Vote();
        vote.setId(id);
        int userId = authUser.getId();
        log.info("update {} for user {}", vote, userId);
        VoteTo updated = checkNotFoundWithId(repository.save(vote, userId, restaurantId), id);
        if (updated.getId() == null) {
            throw new ApplicationException("exception.restaurantNotFound", ErrorType.DATA_ERROR);
        }
    }

    @GetMapping("/filter")
    public List<VoteTo> getFiltered(
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
