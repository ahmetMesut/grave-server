package com.mebitech.grave.hibernate.enumtype;

import io.robe.common.service.search.SearchableEnum;


public enum Gender implements SearchableEnum {

    MALE("Bay"),
    FEMALE("Bayan");

    private String text;

    Gender(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}