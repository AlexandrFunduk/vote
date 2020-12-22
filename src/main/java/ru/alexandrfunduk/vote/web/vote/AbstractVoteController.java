package ru.alexandrfunduk.vote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alexandrfunduk.vote.repository.VoteRepository;

public class AbstractVoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected VoteRepository repository;
}
