package com.ahmetsahinoglu.grave.hibernate.entity;

import com.ahmetsahinoglu.grave.hibernate.enumtype.BurialTime;
import com.ahmetsahinoglu.grave.hibernate.enumtype.Gender;
import com.ahmetsahinoglu.grave.hibernate.enumtype.Grave;
import com.ahmetsahinoglu.grave.hibernate.enumtype.OperationType;
import io.robe.hibernate.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
public class BurialRecord extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String idNumber;

    @Column
    private String fatherName;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private String surname;

    @Column
    private String motherName;

    @Column
    private Date birthDate;

    @Column
    private String birthPlace;

    @Column
    private Date dateOfDeath;

    @Column
    private String causeOfDeath;

    @Column
    private String descriptionOfDeath;

    @Column
    private String placeOfDeath;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    @Column
    private OperationType operationType;

    @Column
    private String provinceOfDeath;

    @Column
    private String districtOfDeath;

    @Column
    private String doctorName;

    @Column
    private String doctorSurname;

    @Column
    private String relatedPersonFullName;

    @Column
    private String relatedDegree;

    @Column
    private String mosque;

    @Column
    private String relatedDescription;

    @Column
    private String mosqueDescription;

    @Column
    private Date burialDate;

    @Enumerated(EnumType.STRING)
    @Column
    private BurialTime burialTime;

    @Column
    private String burialTimeDescription;

    @Column
    private Grave grave;

    @Column
    private String relatedAddress;

    @Column
    private String relatedPhone;

    @Column
    private String relatedCellphone;


    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BurialTime getBurialTime() {
        return burialTime;
    }

    public void setBurialTime(BurialTime burialTime) {
        this.burialTime = burialTime;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public String getDescriptionOfDeath() {
        return descriptionOfDeath;
    }

    public void setDescriptionOfDeath(String descriptionOfDeath) {
        this.descriptionOfDeath = descriptionOfDeath;
    }

    public String getPlaceOfDeath() {
        return placeOfDeath;
    }

    public void setPlaceOfDeath(String placeOfDeath) {
        this.placeOfDeath = placeOfDeath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getProvinceOfDeath() {
        return provinceOfDeath;
    }

    public void setProvinceOfDeath(String provinceOfDeath) {
        this.provinceOfDeath = provinceOfDeath;
    }

    public String getDistrictOfDeath() {
        return districtOfDeath;
    }

    public void setDistrictOfDeath(String districtOfDeath) {
        this.districtOfDeath = districtOfDeath;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSurname() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    public String getRelatedPersonFullName() {
        return relatedPersonFullName;
    }

    public void setRelatedPersonFullName(String relatedPersonFullName) {
        this.relatedPersonFullName = relatedPersonFullName;
    }

    public String getRelatedDegree() {
        return relatedDegree;
    }

    public void setRelatedDegree(String relatedDegree) {
        this.relatedDegree = relatedDegree;
    }

    public String getMosque() {
        return mosque;
    }

    public void setMosque(String mosque) {
        this.mosque = mosque;
    }

    public String getRelatedDescription() {
        return relatedDescription;
    }

    public void setRelatedDescription(String relatedDescription) {
        this.relatedDescription = relatedDescription;
    }

    public String getMosqueDescription() {
        return mosqueDescription;
    }

    public void setMosqueDescription(String mosqueDescription) {
        this.mosqueDescription = mosqueDescription;
    }

    public Date getBurialDate() {
        return burialDate;
    }

    public void setBurialDate(Date burialDate) {
        this.burialDate = burialDate;
    }

    public String getBurialTimeDescription() {
        return burialTimeDescription;
    }

    public void setBurialTimeDescription(String burialTimeDescription) {
        this.burialTimeDescription = burialTimeDescription;
    }

    public Grave getGrave() {
        return grave;
    }

    public void setGrave(Grave grave) {
        this.grave = grave;
    }

    public String getRelatedAddress() {
        return relatedAddress;
    }

    public void setRelatedAddress(String relatedAddress) {
        this.relatedAddress = relatedAddress;
    }

    public String getRelatedPhone() {
        return relatedPhone;
    }

    public void setRelatedPhone(String relatedPhone) {
        this.relatedPhone = relatedPhone;
    }

    public String getRelatedCellphone() {
        return relatedCellphone;
    }

    public void setRelatedCellphone(String relatedCellphone) {
        this.relatedCellphone = relatedCellphone;
    }


}
