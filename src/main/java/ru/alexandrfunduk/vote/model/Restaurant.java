package ru.alexandrfunduk.vote.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Restaurant extends AbstractNamedRegisteredEntity {
    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Date registered) {
        super(id, name, registered);
    }
}
