package ru.alexandrfunduk.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.alexandrfunduk.vote.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoteRepository {
    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "dateTime");

    private final CrudVoteRepository crudRepository;
    private final CrudUserRepository crudUserRepository;

    public VoteRepository(CrudVoteRepository crudRepository, CrudUserRepository crudUserRepository) {
        this.crudRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;
    }

    public Vote save(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getOne(userId));
        return crudRepository.save(vote);
    }

    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    public Vote get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public Vote get(int id, int userId) {
        return crudRepository.findById(id)
                .filter(meal -> meal.getUser().getId() == userId)
                .orElse(null);
    }

    public List<Vote> getAll() {
        return crudRepository.findAll(SORT_DATE);
    }

    public List<Vote> getAll(int userId) {
        return crudRepository.getVotesByUser(userId);
    }

    public List<Vote> getByDateTime(LocalDateTime dateTime) {
        return crudRepository.getVotesByDateTime(dateTime);
    }

    public Vote getByDayAndUser(int userId, LocalDateTime dateTime) {
        return crudRepository.getDayVotesByUser(userId, dateTime);
    }
}
