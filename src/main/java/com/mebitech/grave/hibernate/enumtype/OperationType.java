package com.mebitech.grave.hibernate.enumtype;

import io.robe.common.service.search.SearchableEnum;

public enum OperationType implements SearchableEnum {

    NON_MUNICIPAL("Belediye Dışı"),
    IN_MUNICIPAL("Belediye İçi"),
    BURIED("Defnedildi"),
    WILL_BURIAL("Defnedilecek");

    private String text;

    OperationType(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

}