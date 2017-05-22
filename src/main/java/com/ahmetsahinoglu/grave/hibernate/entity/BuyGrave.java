package com.ahmetsahinoglu.grave.hibernate.entity;

import com.ahmetsahinoglu.grave.hibernate.enumtype.Grave;
import com.ahmetsahinoglu.grave.hibernate.enumtype.ReceiptState;
import io.robe.hibernate.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class BuyGrave extends BaseEntity {

    @Column(unique = true)
    private String userOid;

    @Column
    private String identityNo;

    @Enumerated(EnumType.STRING)
    @Column
    private Grave grave;

    @Column
    private Date buyDate;

    @Column
    private String insular; //ada

    @Column
    private String parcel;

    @Column
    private int startGraveNo;

    @Column
    private int nextFreeGraveCount;

    @Column
    private int freeGraveCount;

    @Column
    private int paidGraveCount;

    @Column
    private int totalPrice;

    @Column
    private int receiptNo;

    @Column
    private Date receiptDate;

    @Enumerated(EnumType.STRING)
    @Column
    private ReceiptState isReceiptCancel;

    @Column
    private String licenceNo;

    @Column
    private Date licenceDate;

    @Column
    private int licenceMortar;


    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }


    public Grave getGrave() {
        return grave;
    }

    public void setGrave(Grave grave) {
        this.grave = grave;
    }

    public ReceiptState getIsReceiptCancel() {
        return isReceiptCancel;
    }

    public void setIsReceiptCancel(ReceiptState isReceiptCancel) {
        this.isReceiptCancel = isReceiptCancel;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
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

    public int getNextFreeGraveCount() {
        return nextFreeGraveCount;
    }

    public void setNextFreeGraveCount(int nextFreeGraveCount) {
        this.nextFreeGraveCount = nextFreeGraveCount;
    }

    public int getFreeGraveCount() {
        return freeGraveCount;
    }

    public void setFreeGraveCount(int freeGraveCount) {
        this.freeGraveCount = freeGraveCount;
    }

    public int getPaidGraveCount() {
        return paidGraveCount;
    }

    public void setPaidGraveCount(int paidGraveCount) {
        this.paidGraveCount = paidGraveCount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(int receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public Date getLicenceDate() {
        return licenceDate;
    }

    public void setLicenceDate(Date licenceDate) {
        this.licenceDate = licenceDate;
    }

    public int getLicenceMortar() {
        return licenceMortar;
    }

    public void setLicenceMortar(int licenceMortar) {
        this.licenceMortar = licenceMortar;
    }


}
