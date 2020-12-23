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
    private Map<String, Integer> dishPrise;

    public MenuTo() {
    }

    public MenuTo(Integer id, @NotBlank LocalDate day, @NotNull Integer restaurantId, @NotNull Map<String, Integer> dishPrise) {
        super(id);
        this.day = day;
        this.restaurantId = restaurantId;
        this.dishPrise = dishPrise;
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

    public Map<String, Integer> getDishPrise() {
        return dishPrise;
    }

    public void setDishPrise(Map<String, Integer> dishPrise) {
        this.dishPrise = dishPrise;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", day=" + day +
                ", restaurantId=" + restaurantId +
                ", dishPrise=" + dishPrise +
                '}';
    }
}
