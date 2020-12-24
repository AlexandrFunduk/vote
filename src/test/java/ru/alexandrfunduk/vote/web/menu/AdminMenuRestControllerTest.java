package ru.alexandrfunduk.vote.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alexandrfunduk.vote.RestaurantTestData;
import ru.alexandrfunduk.vote.model.Menu;
import ru.alexandrfunduk.vote.repository.MenuRepository;
import ru.alexandrfunduk.vote.to.MenuTo;
import ru.alexandrfunduk.vote.util.exception.NotFoundException;
import ru.alexandrfunduk.vote.web.AbstractControllerTest;
import ru.alexandrfunduk.vote.web.ExceptionInfoHandler;
import ru.alexandrfunduk.vote.web.json.JsonUtil;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.alexandrfunduk.vote.MenuTestData.*;
import static ru.alexandrfunduk.vote.TestUtil.*;
import static ru.alexandrfunduk.vote.UserTestData.admin;
import static ru.alexandrfunduk.vote.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.alexandrfunduk.vote.web.menu.AdminMenuRestController.REST_URL;

class AdminMenuRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuRepository repository;

    @Test
    void createWithLocation() throws Exception{
        MenuTo newMenuTo = getNewTo();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuTo))
                .with(userHttpBasic(admin)));
        Menu created = readFromJson(actions, Menu.class);
        int newId = created.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(repository.get(newId), newMenu);
    }

    @Test
    void createInvalid() throws Exception {
        MenuTo invalid = new MenuTo(null, null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void createDuplicate() throws Exception {
        MenuTo invalid =  new MenuTo(null, menuTo2.getDay(), RestaurantTestData.RESTAURANT_ID+1, Map.of("F",50));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_MENU_TODAY));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + MENU_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.get(MENU_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        MenuTo updated = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        MENU_MATCHER.assertMatch(repository.get(MENU_ID), getUpdated());
    }

    @Test
    void updateInvalid() throws Exception {
        MenuTo invalid =  new MenuTo(null, null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateDuplicate() throws Exception {
        MenuTo invalid =  new MenuTo(MENU_ID, menuTo2.getDay(), RestaurantTestData.RESTAURANT_ID+1, Map.of("F",50));
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_MENU_TODAY));
    }
}