package ru.alexandrfunduk.vote.web.vote;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.alexandrfunduk.vote.model.Vote;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractVoteRestController{
    static final String REST_URL = "/rest/admin/vote";

    @GetMapping
    public List<Vote> getAll(@RequestParam @Nullable Integer userId) {
        if (userId==null) {
            log.info("getAll");
            return super.getAll();
        }
        return super.getAll(userId);
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get {}", id);
        return super.get(id);
    }
}
