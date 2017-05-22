package com.ahmetsahinoglu.grave.hibernate.entity;

import com.ahmetsahinoglu.grave.hibernate.enumtype.PriceType;
import io.robe.hibernate.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table
public class GravePrice extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private PriceType priceType;

    @Column
    private int unitPrice;

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }


}
