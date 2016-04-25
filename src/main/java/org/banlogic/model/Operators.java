package org.banlogic.model;

public enum Operators {
    BELIEVES("|="),
    SEES("|~"),
    CONTROLS("=>"),
    FRESH("#(%s)");
//    KEY,
//    PK,
//    SECRET,
//    COMBINATION;

    private String value;

    private Operators(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
