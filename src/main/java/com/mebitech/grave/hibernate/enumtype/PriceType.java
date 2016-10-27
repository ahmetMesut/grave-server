package com.mebitech.grave.hibernate.enumtype;

import io.robe.common.service.search.SearchableEnum;

public enum PriceType implements SearchableEnum {

    NEXT_TO_PAID("Ücretli Yanı"),
    NEXT_TO_FREE("Ücretsiz Yanı"),
    FREE("Ücretsiz");

    private String text;

    PriceType(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}