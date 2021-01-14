package ru.alexandrfunduk.vote.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alexandrfunduk.vote.repository.VoteRepository;
import ru.alexandrfunduk.vote.to.VoteTo;
import ru.alexandrfunduk.vote.util.VoteUtil;
import ru.alexandrfunduk.vote.util.exception.NotFoundException;
import ru.alexandrfunduk.vote.web.AbstractControllerTest;
import ru.alexandrfunduk.vote.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.alexandrfunduk.vote.RestaurantTestData.RESTAURANT_ID;
import static ru.alexandrfunduk.vote.TestUtil.*;
import static ru.alexandrfunduk.vote.UserTestData.*;
import static ru.alexandrfunduk.vote.VoteTestDate.*;
import static ru.alexandrfunduk.vote.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.alexandrfunduk.vote.util.exception.ErrorType.VALIDATION_ERROR;

class ProfileVoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileVoteRestController.REST_URL + "/";

    @Autowired
    VoteRepository repository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileVoteRestController.REST_URL)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTo4, voteTo1));
    }

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTo1));
    }

    @Test
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today")
                .with(userHttpBasic(user2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTo7));
    }

    @Test
    void createBeforeVotingTime() throws Exception {
        VoteUtil.setImitation(true);
        VoteUtil.setEnableCreateAndUpdate(true);
        VoteTo newVoteTo = getNewTo();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(RESTAURANT_ID))
                .with(userHttpBasic(user1)));
        VoteTo created = readFromJson(actions, VoteTo.class);
        int newId = created.id();
        newVoteTo.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVoteTo);
        VOTE_TO_MATCHER.assertMatch(repository.get(newId), newVoteTo);
    }

    @Test
    void createInvalidBeforeVotingTime() throws Exception {
        VoteUtil.setImitation(true);
        VoteUtil.setEnableCreateAndUpdate(true);
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(NOT_FOUND))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateBeforeVotingTime()  throws Exception {
        VoteUtil.setImitation(true);
        VoteUtil.setEnableCreateAndUpdate(true);
        perform(MockMvcRequestBuilders.put(REST_URL + (7+VOTE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user3))
                .content(JsonUtil.writeValue(RESTAURANT_ID+1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VOTE_TO_MATCHER.assertMatch(repository.get(7+VOTE_ID), voteToUpdated);
    }

    @Test
    void updateAfterVotingTime()  throws Exception {
        VoteUtil.setImitation(true);
        VoteUtil.setEnableCreateAndUpdate(false);
        perform(MockMvcRequestBuilders.put(REST_URL + (7+VOTE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user3))
                .content(JsonUtil.writeValue(RESTAURANT_ID+1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void getFiltered() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2020-12-02")
                .param("endDate", "2020-12-04")
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(voteTo4)));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}