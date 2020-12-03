package ru.alexandrfunduk.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.alexandrfunduk.vote.model.Vote;

import java.util.List;

@Repository
public class VoteRepository {
    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "day");

    private final CrudVoteRepository crudRepository;

    public VoteRepository(CrudVoteRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Vote get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Vote> getAll() {
        return crudRepository.findAll(SORT_DATE);
    }
}
