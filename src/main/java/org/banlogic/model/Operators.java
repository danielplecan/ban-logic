package org.banlogic.model;

public enum Operators {

    BELIEVES("|="),
    //    SEES("<|"),
    //    CONTROLS("=>"),
    //    SAID("|~"),
    //    FRESH("#(%s)"),
    //    KEY("<-(%s)->"),
    //    PK,
    //    SECRET("<=(%s)=>"),
    //    COMBINATION;
    //    BELIEVES("believes"),
    SEES("<|"),
    CONTROLS("=>"),
    SAID("|~"),
    FRESH("#(%s)"),
    KEY("<-%s->"),
    PK("pk(%s,%s)"),
    SECRET("<=%s=>"),
    COMBINATION("<%s>_%s");

    private final String value;

    private Operators(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
