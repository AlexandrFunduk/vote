package ru.alexandrfunduk.vote.model;

import java.time.LocalDateTime;

public class Vote extends AbstractBaseEntity {
    LocalDateTime dateTime;
    User user;
    Restaurant restaurant;
}
