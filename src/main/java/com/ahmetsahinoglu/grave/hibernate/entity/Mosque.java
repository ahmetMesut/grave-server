package com.ahmetsahinoglu.grave.hibernate.entity;

import io.robe.hibernate.entity.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class Mosque extends BaseEntity {

    @NotEmpty(message = "Cami adı boş olamaz")
    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
