package ru.alexandrfunduk.vote;

import ru.alexandrfunduk.vote.model.Vote;
import ru.alexandrfunduk.vote.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static ru.alexandrfunduk.vote.RestaurantTestData.RESTAURANT_ID;
import static ru.alexandrfunduk.vote.UserTestData.USER_ID;
import static ru.alexandrfunduk.vote.model.AbstractBaseEntity.START_SEQ;

public class VoteTestDate {
    public static final TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(VoteTo.class);
    public static final int VOTE_ID = START_SEQ + 14;

    /*public static final Vote vote1 = new Vote(VOTE_ID, LocalDate.of(2020, 12, 1), UserTestData.user1, RestaurantTestData.restaurant1);
    public static final Vote vote2 = new Vote(VOTE_ID + 1, LocalDate.of(2020, 12, 1), UserTestData.user2, RestaurantTestData.restaurant2);
    public static final Vote vote3 = new Vote(VOTE_ID + 2, LocalDate.of(2020, 12, 1), UserTestData.user3, RestaurantTestData.restaurant3);
    public static final Vote vote4 = new Vote(VOTE_ID + 3, LocalDate.of(2020, 12, 2), UserTestData.user1, RestaurantTestData.restaurant1);
    public static final Vote vote5 = new Vote(VOTE_ID + 4, LocalDate.of(2020, 12, 2), UserTestData.user2, RestaurantTestData.restaurant2);
    public static final Vote vote6 = new Vote(VOTE_ID + 5, LocalDate.of(2020, 12, 2), UserTestData.user3, RestaurantTestData.restaurant3);
    public static final Vote vote8 = new Vote(VOTE_ID + 6, LocalDate.now(), UserTestData.user2, RestaurantTestData.restaurant1);
    public static final Vote vote9 = new Vote(VOTE_ID + 7, LocalDate.now(), UserTestData.user3, RestaurantTestData.restaurant1);*/
    public static final VoteTo voteTo1 = new VoteTo(VOTE_ID, LocalDate.of(2020, 12, 1), USER_ID, RESTAURANT_ID);
    public static final VoteTo voteTo2 = new VoteTo(VOTE_ID + 1, LocalDate.of(2020, 12, 1), USER_ID+1, RESTAURANT_ID+1);
    public static final VoteTo voteTo3 = new VoteTo(VOTE_ID + 2, LocalDate.of(2020, 12, 1), USER_ID+2, RESTAURANT_ID+2);
    public static final VoteTo voteTo4 = new VoteTo(VOTE_ID + 3, LocalDate.of(2020, 12, 2), USER_ID, RESTAURANT_ID+1);
    public static final VoteTo voteTo5 = new VoteTo(VOTE_ID + 4, LocalDate.of(2020, 12, 2), USER_ID+1, RESTAURANT_ID+1);
    public static final VoteTo voteTo6 = new VoteTo(VOTE_ID + 5, LocalDate.of(2020, 12, 2), USER_ID+2, RESTAURANT_ID);
    public static final VoteTo voteTo7 = new VoteTo(VOTE_ID + 6, LocalDate.now(), USER_ID+1, RESTAURANT_ID);
    public static final VoteTo voteTo8 = new VoteTo(VOTE_ID + 7, LocalDate.now(), USER_ID+2, RESTAURANT_ID);
    public static final VoteTo voteToUpdated = new VoteTo(VOTE_ID + 7, LocalDate.now(), USER_ID+2, RESTAURANT_ID+1);
    public static final List<VoteTo> voteTos = List.of(voteTo8, voteTo7,voteTo6,voteTo5,voteTo4,voteTo3,voteTo2,voteTo1);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), UserTestData.user1, RestaurantTestData.restaurant1);
    }

    public static VoteTo getNewTo() {
        return new VoteTo(null, LocalDate.now(), USER_ID, RESTAURANT_ID);
    }
}
