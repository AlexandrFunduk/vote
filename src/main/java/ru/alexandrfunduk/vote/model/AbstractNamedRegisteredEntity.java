package ru.alexandrfunduk.vote.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@MappedSuperclass
public abstract class AbstractNamedRegisteredEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    protected AbstractNamedRegisteredEntity() {
    }

    protected AbstractNamedRegisteredEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    protected AbstractNamedRegisteredEntity(Integer id, String name, Date registered) {
        super(id);
        this.name = name;
        this.registered = registered;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return super.toString() + " (" +
                "name='" + name + '\'' +
                ", registered=" + registered +
                ')';
    }
}