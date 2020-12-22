package ru.alexandrfunduk.vote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.to.VoteTo;
import ru.alexandrfunduk.vote.util.exception.ApplicationException;
import ru.alexandrfunduk.vote.util.exception.ErrorType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
        if (dateTime.toLocalTime().isBefore(LocalTime.of(11, 0, 0))) {
            vote.setDate(dateTime.toLocalDate());
            vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
            vote.setUser(crudUserRepository.getOne(userId));
            return new VoteTo(crudRepository.save(vote).getId(), vote.getDate(), restaurantId, userId);
        }
        throw new ApplicationException("edit vote after 11:00", ErrorType.VALIDATION_ERROR);
    }

    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    public VoteTo get(int id, int userId) {
        return crudRepository.findById(id)
                .filter(meal -> meal.getUser().getId() == userId)
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), userId))
                .orElse(null);
    }

    public List<VoteTo> getAll() {
        return crudRepository.findAll(Sort.by(Sort.Direction.DESC, "date")).stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getUser().getId()))
                .collect(Collectors.toList());
    }

    public List<VoteTo> getAll(int userId) {
        return crudRepository.getAll(userId).stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), userId))
                .collect(Collectors.toList());
    }

    public VoteTo getByDay(LocalDate date, int userId) {
        Vote vote = crudRepository.getVoteByDate(date, userId);
        return new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), userId);
    }

    public List<VoteTo> getByDay(LocalDate date) {
        return crudRepository.getVotesByDate(date).stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getUser().getId()))
                .collect(Collectors.toList());
    }

    public List<VoteTo> getBetween(int userId, LocalDate startDate, LocalDate endDate) {
        return crudRepository.getBetweenByUser(startDate, endDate, userId).stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), userId))
                .collect(Collectors.toList());
    }

    public List<VoteTo> getBetween(LocalDate startDate, LocalDate endDate) {
        return crudRepository.getBetween(startDate, endDate).stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getUser().getId()))
                .collect(Collectors.toList());
    }
}
