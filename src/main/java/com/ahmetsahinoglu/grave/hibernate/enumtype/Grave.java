package com.ahmetsahinoglu.grave.hibernate.enumtype;

import io.robe.common.service.search.SearchableEnum;

public enum Grave implements SearchableEnum {
    AGM("Asrı Mezarlığı"),
    BGM("Bağçeşme Mezarlığı"),
    KBBM("KBB Kent Mezarlığı"),
    KM("Kuruçeşme Mezarlığı"),
    TM("Tavşantepe Mezarlığı");

    private String text;


    Grave(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}

