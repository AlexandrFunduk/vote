package ru.alexandrfunduk.vote.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.alexandrfunduk.vote.View;
import ru.alexandrfunduk.vote.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Vote extends AbstractBaseEntity {

    @NotNull
    @Column(name = "date")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(groups = View.Persist.class)
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull(groups = View.Persist.class)
    Restaurant restaurant;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                ", restaurant=" + restaurant +
                '}';
    }
}
