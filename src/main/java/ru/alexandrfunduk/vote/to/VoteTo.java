package ru.alexandrfunduk.vote.to;

import ru.alexandrfunduk.vote.HasId;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VoteTo extends BaseTo implements HasId {

    @NotNull
    LocalDate date;

    @NotNull
    Integer restaurantId;

    @NotNull
    Integer userId;

    public VoteTo() {
    }

    public VoteTo(Integer id, @NotNull LocalDate date, Integer restaurantId, Integer userId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", date=" + date +
                ", restaurantId=" + restaurantId +
                ", userId=" + userId +
                '}';
    }
}
