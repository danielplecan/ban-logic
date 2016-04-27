package org.banlogic.model;

public enum Operators {

    BELIEVES("believes"),
    SEES("sees"),
    CONTROLS("controls"),
    SAID("said"),
    FRESH("fresh(%s)"),
    KEY("<-(%s)->");
//    PK,
//    SECRET,
//    COMBINATION;

    private final String value;

    private Operators(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
