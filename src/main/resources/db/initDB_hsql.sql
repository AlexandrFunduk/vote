DROP TABLE user_roles IF EXISTS;
DROP TABLE dish_prise IF EXISTS;
DROP TABLE menu IF EXISTS;
DROP TABLE vote IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx
    ON RESTAURANT (name);

CREATE TABLE menu
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    day           TIMESTAMP DEFAULT now() NOT NULL,
    restaurant_id INTEGER                 NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_unique_day_restaurant_name_idx
    ON MENU (day,restaurant_id);

CREATE TABLE dish_prise
(
    menu_id INTEGER NOT NULL,
    dish    VARCHAR(255) NOT NULL,
    prise   INTEGER,
    CONSTRAINT menu_id_dish_idx UNIQUE (menu_id, dish),
    FOREIGN KEY (menu_id) REFERENCES MENU (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date      TIMESTAMP DEFAULT now() NOT NULL,
    restaurant_id INTEGER                 NOT NULL,
    user_id       INTEGER                 NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_user_date_time_idx
    ON VOTE (user_id, date);