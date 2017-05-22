package com.ahmetsahinoglu.grave.hibernate.enumtype;

import io.robe.common.service.search.SearchableEnum;

public enum ReceiptState implements SearchableEnum {

    YES("Evet"),
    NO("Hayır");

    private String text;

    ReceiptState(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
