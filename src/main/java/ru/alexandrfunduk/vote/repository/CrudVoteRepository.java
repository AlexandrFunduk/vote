package ru.alexandrfunduk.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrfunduk.vote.model.User;
import ru.alexandrfunduk.vote.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote vote WHERE vote.id=:id")
    int delete(@Param("id") int id);

    List<Vote> getVotesByUser(User user);

    List<Vote> getVotesByDateTime(LocalDateTime dateTime);

    @Modifying
    @Query("SELECT vote FROM Vote vote WHERE vote.dateTime=:dateTime and vote.user=:user")
    Vote getDayVotesByUser(@Param("user") User user, @Param("dateTime") LocalDateTime dateTime);
}
