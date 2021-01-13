package ru.alexandrfunduk.vote.to;

import org.springframework.format.annotation.DateTimeFormat;
import ru.alexandrfunduk.vote.HasId;
import ru.alexandrfunduk.vote.util.DateTimeUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

public class MenuTo extends BaseTo implements HasId {
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate day;

    @NotNull
    private Integer restaurantId;

    @NotNull
    private Map<String, Integer> dishPrice;

    public MenuTo() {
    }

    public MenuTo(Integer id, @NotBlank LocalDate day, @NotNull Integer restaurantId, @NotNull Map<String, Integer> dishPrice) {
        super(id);
        this.day = day;
        this.restaurantId = restaurantId;
        this.dishPrice = dishPrice;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Map<String, Integer> getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Map<String, Integer> dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", day=" + day +
                ", restaurantId=" + restaurantId +
                ", dishPrice=" + dishPrice +
                '}';
    }
}
