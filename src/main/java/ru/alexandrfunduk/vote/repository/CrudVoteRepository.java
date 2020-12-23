package ru.alexandrfunduk.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote vote WHERE vote.id=:id and vote.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.user.id=:user_id ORDER BY vote.date desc, vote.id desc")
    List<Vote> getAll(@Param("user_id") int userId);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.date>=:startDate and vote.date<:endDate and vote.user.id=:user_id  ORDER BY vote.date desc, vote.id desc")
    List<Vote> getBetweenByUser(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("user_id") int userId);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.date>=:startDate and vote.date<:endDate ORDER BY vote.date desc, vote.id desc ")
    List<Vote> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.date=:date ORDER BY vote.date desc , vote.id desc ")
    List<Vote> getByDate(@Param("date") LocalDate date);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.date=:date and vote.user.id=:user_id")
    List<Vote> getVoteByDate(@Param("date") LocalDate date, @Param("user_id") int userId);
}
