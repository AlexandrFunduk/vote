package ru.alexandrfunduk.vote;

import ru.alexandrfunduk.vote.model.Role;
import ru.alexandrfunduk.vote.model.User;
import ru.alexandrfunduk.vote.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;

import static ru.alexandrfunduk.vote.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final User user1 = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User user2 = new User(USER_ID+1, "User1", "user1@yandex.ru", "123456", Role.USER);
    public static final User user3 = new User(USER_ID+2, "User2", "user2@yandex.ru", "654321", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user1);
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
