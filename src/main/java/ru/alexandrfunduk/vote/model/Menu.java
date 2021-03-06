package ru.alexandrfunduk.vote.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.alexandrfunduk.vote.View;
import ru.alexandrfunduk.vote.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "day"}, name = "restaurant_id_day_idx")})
public class Menu extends AbstractBaseEntity {
    @Column
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dish_price")
    @MapKeyColumn(name = "dish")
    @Column(name = "price")
    @BatchSize(size = 200)
    @NotNull
    private Map<String, Integer> dishPrice;

    public Menu(LocalDate day, Restaurant restaurant, Map<String, Integer> dishPrice) {
        this.day = day;
        this.restaurant = restaurant;
        this.dishPrice = dishPrice;
    }

    public Menu(Integer id, LocalDate day, Restaurant restaurant, Map<String, Integer> dishPrice) {
        super(id);
        this.day = day;
        this.restaurant = restaurant;
        this.dishPrice = dishPrice;
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

    public Map<String, Integer> getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Map<String, Integer> dish_price) {
        this.dishPrice = dish_price;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", day=" + day +
                ", restaurant=" + restaurant +
                ", dishPrice=" + dishPrice +
                '}';
    }
}
