package ru.alexandrfunduk.vote.repository;

import org.springframework.stereotype.Repository;
import ru.alexandrfunduk.vote.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    public Vote save(Vote vote, int userId, int restaurantId) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (!vote.isNew() && (get(vote.getId(), userId) == null || !dateTime.toLocalTime().isAfter(LocalTime.of(11, 0, 0)))) {
            return null;
        }
        vote.setDateTime(dateTime);
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        vote.setUser(crudUserRepository.getOne(userId));
        return crudRepository.save(vote);
    }

    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    public Vote get(int id, int userId) {
        return crudRepository.findById(id)
                .filter(meal -> meal.getUser().getId() == userId)
                .orElse(null);
    }

    public List<Vote> getAll(int userId) {
        return crudRepository.getAll(userId);
    }

    public Vote getByDay(int userId, LocalDate date) {
        List<Vote> votes = getBetween(userId, date, date.plusDays(1));
        return votes.isEmpty() ? null : votes.get(0);
    }

    public List<Vote> getBetween(int userId, LocalDate startDate, LocalDate endDate) {
        return crudRepository.getBetween(userId, startDate.atStartOfDay(), endDate.atStartOfDay());
    }
}
