package ru.alexandrfunduk.vote.web.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController extends AbstractVoteRestController {
    static final String REST_URL = "/rest/profile/vote";

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll");
        return super.getAll(SecurityUtil.authUserId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestBody Vote vote) {
        log.info("create {}", vote);
        Vote created = super.create(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        super.delete(id, SecurityUtil.authUserId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int id) {
        log.info("update {} with id={}", vote, id);
        Assert.notNull(vote, "user must not be null");
        super.update(vote, vote.id());
    }
}
