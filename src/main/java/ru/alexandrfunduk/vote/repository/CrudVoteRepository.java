package ru.alexandrfunduk.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote vote WHERE vote.id=:id and vote.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.user.id=:user_id ORDER BY vote.dateTime desc")
    List<Vote> getAll(@Param("user_id") int userId);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.dateTime>=:startTime and vote.dateTime<:endDate and vote.user.id=:user_id  ORDER BY vote.dateTime desc")
    List<Vote> getBetween(@Param("user_id") int userId, @Param("startTime") LocalDateTime startTime, @Param("endDate") LocalDateTime endDate);
}
