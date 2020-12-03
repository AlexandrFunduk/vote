package ru.alexandrfunduk.vote.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Entity
public class Menu extends AbstractBaseEntity {
    @Column
    @NotBlank
    private LocalDate day;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private Restaurant restaurant;

    @ElementCollection
    @CollectionTable(name = "dish_prise")
    @MapKeyColumn(name = "dish")
    @Column(name = "prise")
    private Map<String, Integer> dish_prise;

    public Menu(LocalDate day, Restaurant restaurant, Map<String, Integer> dish_prise) {
        this.day = day;
        this.restaurant = restaurant;
        this.dish_prise = dish_prise;
    }

    public Menu(Integer id, LocalDate day, Restaurant restaurant, Map<String, Integer> dish_prise) {
        super(id);
        this.day = day;
        this.restaurant = restaurant;
        this.dish_prise = dish_prise;
    }

    public Menu() {
        super();
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Map<String, Integer> getDish_prise() {
        return dish_prise;
    }

    public void setDish_prise(Map<String, Integer> dish_prise) {
        this.dish_prise = dish_prise;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", day=" + day +
                ", restaurant=" + restaurant +
                ", dish_prise=" + dish_prise +
                '}';
    }
}
