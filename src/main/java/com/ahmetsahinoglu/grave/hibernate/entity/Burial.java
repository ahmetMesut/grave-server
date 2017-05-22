package com.ahmetsahinoglu.grave.hibernate.entity;

import com.ahmetsahinoglu.grave.hibernate.enumtype.Grave;
import com.ahmetsahinoglu.grave.hibernate.enumtype.GraveState;
import com.ahmetsahinoglu.grave.hibernate.enumtype.PriceType;
import io.robe.common.service.search.SearchFrom;
import io.robe.common.service.search.SearchIgnore;
import io.robe.hibernate.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Burial extends BaseEntity {

    @Column
    private long registerNo = 1;
    @Column
    private String userOid;

    @SearchIgnore
    @SearchFrom(entity = BurialRecord.class, target = {"name", "surname"})
    @Column(unique = true)
    private String lastBurialOid;
    @Column
    @Enumerated(EnumType.STRING)
    private PriceType graveType;
    @Column
    @Enumerated(EnumType.STRING)
    private Grave grave;
    @Column
    private String insular; //ada
    @Column
    private String parcel; //parsel
    @Column
    private int startGraveNo = 1; //sıra no
    @Column
    private Date buyDate;
    @Column
    private GraveState graveState;

    public long getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(long registerNo) {
        this.registerNo = registerNo;
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    public String getLastBurialOid() {
        return lastBurialOid;
    }

    public void setLastBurialOid(String lastBurialOid) {
        this.lastBurialOid = lastBurialOid;
    }

    public Grave getGrave() {
        return grave;
    }

    public void setGrave(Grave grave) {
        this.grave = grave;
    }

    public String getInsular() {
        return insular;
    }

    public void setInsular(String insular) {
        this.insular = insular;
    }

    public String getParcel() {
        return parcel;
    }

    public void setParcel(String parcel) {
        this.parcel = parcel;
    }

    public int getStartGraveNo() {
        return startGraveNo;
    }

    public void setStartGraveNo(int startGraveNo) {
        this.startGraveNo = startGraveNo;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public PriceType getGraveType() {
        return graveType;
    }

    public void setGraveType(PriceType graveType) {
        this.graveType = graveType;
    }

    public GraveState getGraveState() {
        return graveState;
    }

    public void setGraveState(GraveState graveState) {
        this.graveState = graveState;
    }
}
