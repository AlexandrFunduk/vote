package ru.alexandrfunduk.vote.model;

import java.time.LocalDate;
import java.util.Map;

public class Menu extends AbstractBaseEntity {
    private LocalDate day;
    private Restaurant restaurant;
    private Map<String, Integer> dish_prise;
}
