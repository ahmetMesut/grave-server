package com.mebitech.grave.hibernate.entity;

import io.robe.hibernate.entity.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class DeadOfCause extends BaseEntity {

    @NotEmpty
    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
