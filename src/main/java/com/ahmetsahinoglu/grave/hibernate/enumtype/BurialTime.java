package com.ahmetsahinoglu.grave.hibernate.enumtype;

import io.robe.common.service.search.SearchableEnum;


public enum BurialTime implements SearchableEnum {

    NONE("Bilinmiyor"),
    MORNING("Sabah"),
    NOON("Öğle"),
    AFTERNOON("İkindi");

    private String text;

    BurialTime(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}