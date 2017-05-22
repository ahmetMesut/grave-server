package com.ahmetsahinoglu.grave.hibernate.enumtype;

import io.robe.common.service.search.SearchableEnum;

public enum GraveState implements SearchableEnum {


    NULL("Boş"),
    FULL("Dolu"),
    OVER_BURIAL("Üstüne Defnedildi"),
    CANCELLED("İptal Edildi");

    private String text;


    GraveState(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
