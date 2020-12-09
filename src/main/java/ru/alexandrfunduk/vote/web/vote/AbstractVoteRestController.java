package ru.alexandrfunduk.vote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.repository.VoteRepository;
import ru.alexandrfunduk.vote.web.SecurityUtil;

import java.util.List;

import static ru.alexandrfunduk.vote.util.ValidationUtil.*;

public abstract class AbstractVoteRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteRepository repository;

    public List<Vote> getAll() {
        return repository.getAll();
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Vote get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Vote create(Vote vote) {
        int userId = SecurityUtil.authUserId();
        checkNew(vote);
        log.info("create {} for user {}", vote, userId);
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Vote vote, int id) {
        assureIdConsistent(vote, id);
        Assert.notNull(vote, "user must not be null");
        int userId = SecurityUtil.authUserId();
        log.info("update {} for user {}", vote, userId);
        checkNotFoundWithId(repository.save(vote, userId), vote.id());
    }
}
