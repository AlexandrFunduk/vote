package ru.alexandrfunduk.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.Restaurant;
import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.to.VoteTo;
import ru.alexandrfunduk.vote.util.VoteUtil;
import ru.alexandrfunduk.vote.util.exception.ApplicationException;
import ru.alexandrfunduk.vote.util.exception.ErrorType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFound;
import static ru.alexandrfunduk.vote.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class VoteRepository {
    private final CrudVoteRepository crudRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public VoteRepository(CrudVoteRepository crudRepository, CrudUserRepository crudUserRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public VoteTo save(Vote vote, int userId, int restaurantId) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (!vote.isNew() && (get(vote.getId(), userId) == null)) {
            return null;
        }
        if (VoteUtil.enableCreateAndUpdate(dateTime)) {
            vote.setDate(dateTime.toLocalDate());
            vote.setUser(crudUserRepository.getOne(userId));
            Restaurant restaurant = crudRestaurantRepository.findById(restaurantId).orElse(null);
            vote.setRestaurant(restaurant);
            if (restaurant == null) {
                return new VoteTo();
            }
            Vote dbVote = crudRepository.save(vote);
            return VoteUtil.asTo(dbVote, userId);
        }
        throw new ApplicationException("exception.vote.afterVotingTime", ErrorType.VALIDATION_ERROR);
    }

    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    public VoteTo get(int id) {
        return checkNotFoundWithId(crudRepository.findById(id)
                .map(VoteUtil::asTo)
                .orElse(null), id);
    }

    public VoteTo get(int id, int userId) {
        return checkNotFoundWithId(crudRepository.findById(id)
                .filter(meal -> meal.getUser().getId() == userId)
                .map(vote -> VoteUtil.asTo(vote, userId))
                .orElse(null), id);
    }

    public List<VoteTo> getAll() {
        return crudRepository.findAll(Sort.by("date").descending().and(Sort.by("id").descending())).stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }

    public List<VoteTo> getAll(int userId) {
        return checkNotFound(crudRepository.getAll(userId).stream()
                .map(vote -> VoteUtil.asTo(vote, userId))
                .collect(Collectors.toList()), "Not found entity");
    }

    public VoteTo getByDay(LocalDate date, int userId) {
        return checkNotFound(crudRepository.getByDate(date, userId).stream()
                .map(vote -> VoteUtil.asTo(vote, userId))
                .findFirst()
                .orElse(null), "Not found entity");
    }

    public List<VoteTo> getByDay(LocalDate date) {
        return crudRepository.getByDate(date).stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }

    public List<VoteTo> getBetween(int userId, LocalDate startDate, LocalDate endDate) {
        return crudRepository.getBetweenByUser(startDate, endDate, userId).stream()
                .map(vote -> VoteUtil.asTo(vote, userId))
                .collect(Collectors.toList());
    }

    public List<VoteTo> getBetween(LocalDate startDate, LocalDate endDate) {
        return crudRepository.getBetween(startDate, endDate).stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }
}
