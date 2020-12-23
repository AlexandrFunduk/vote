package ru.alexandrfunduk.vote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alexandrfunduk.vote.model.Restaurant;
import ru.alexandrfunduk.vote.repository.RestaurantRepository;
import ru.alexandrfunduk.vote.util.exception.NotFoundException;
import ru.alexandrfunduk.vote.web.AbstractControllerTest;
import ru.alexandrfunduk.vote.web.ExceptionInfoHandler;
import ru.alexandrfunduk.vote.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.alexandrfunduk.vote.RestaurantTestData.*;
import static ru.alexandrfunduk.vote.TestUtil.*;
import static ru.alexandrfunduk.vote.UserTestData.admin;
import static ru.alexandrfunduk.vote.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.alexandrfunduk.vote.web.restaurant.AdminRestaurantRestController.REST_URL;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(userHttpBasic(admin)));
        Restaurant created = readFromJson(actions, Restaurant.class);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.get(newId), newRestaurant);
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, "r");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + RESTAURANT_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.get(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(repository.get(RESTAURANT_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, "r");
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(null, restaurant2.getName());
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT));
    }

    @Test
    void createDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(null, restaurant2.getName());
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT));
    }
}