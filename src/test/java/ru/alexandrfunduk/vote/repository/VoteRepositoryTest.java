package ru.alexandrfunduk.vote.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.alexandrfunduk.vote.RestaurantTestData;
import ru.alexandrfunduk.vote.TimingExtension;
import ru.alexandrfunduk.vote.UserTestData;
import ru.alexandrfunduk.vote.VoteTestDate;
import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.to.VoteTo;
import ru.alexandrfunduk.vote.util.VoteUtil;
import ru.alexandrfunduk.vote.util.exception.ApplicationException;
import ru.alexandrfunduk.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alexandrfunduk.vote.VoteTestDate.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
class VoteRepositoryTest {

    @Autowired
    private VoteRepository repository;

    @Test
    void saveBeforeVotingTime() {
        VoteUtil.setImitation(true);
        VoteUtil.setEnableCreateAndUpdate(true);
        VoteTo created = repository.save(VoteTestDate.getNew(), UserTestData.USER_ID, RestaurantTestData.RESTAURANT_ID);
        int newId = created.id();
        Vote newVote = VoteTestDate.getNew();
        newVote.setId(newId);

        VoteTo voteTo = new VoteTo(newId, newVote.getDate(), newVote.getUser().getId(), newVote.getRestaurant().getId());
        VOTE_TO_MATCHER.assertMatch(created, voteTo);
        VOTE_TO_MATCHER.assertMatch(repository.get(newId), voteTo);
    }

    @Test
    void saveAfterVotingTime() {
        VoteUtil.setImitation(true);
        VoteUtil.setEnableCreateAndUpdate(false);
        assertThrows(ApplicationException.class, () -> repository.save(VoteTestDate.getNew(), UserTestData.USER_ID, RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void delete() {
        repository.delete(VoteTestDate.VOTE_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> repository.get(VoteTestDate.VOTE_ID));
    }

    @Test
    void get() {
        VoteTo voteTo = repository.get(VoteTestDate.VOTE_ID);
        VOTE_TO_MATCHER.assertMatch(voteTo, VoteTestDate.voteTo1);
    }

    @Test
    void getAll() {
        List<VoteTo> all = repository.getAll();
        VOTE_TO_MATCHER.assertMatch(all, voteTo8, voteTo7, voteTo6, voteTo5, voteTo4, voteTo3, voteTo2, voteTo1);
    }

    @Test
    void GetAllByUser() {
        List<VoteTo> all = repository.getAll(UserTestData.USER_ID);
        VOTE_TO_MATCHER.assertMatch(all, voteTo4, voteTo1);
    }

    @Test
    void getByDay() {
        List<VoteTo> voteTo = repository.getByDay(LocalDate.of(2020, 12, 1));
        VOTE_TO_MATCHER.assertMatch(voteTo, voteTo3, voteTo2, voteTo1);
    }

    @Test
    void getByDayAndUser() {
        VoteTo voteTo = repository.getByDay(LocalDate.of(2020, 12, 1), UserTestData.USER_ID);
        VOTE_TO_MATCHER.assertMatch(voteTo, voteTo1);
    }

    @Test
    void getBetween() {
        List<VoteTo> voteTo = repository.getBetween(LocalDate.of(2020, 12, 1), LocalDate.now());
        VOTE_TO_MATCHER.assertMatch(voteTo, voteTo6, voteTo5, voteTo4, voteTo3, voteTo2, voteTo1);
    }

    @Test
    void testGetBetween() {
        List<VoteTo> voteTo = repository.getBetween(UserTestData.USER_ID, LocalDate.of(2020, 12, 1), LocalDate.now());
        VOTE_TO_MATCHER.assertMatch(voteTo, voteTo4, voteTo1);
    }
}