package org.banlogic.model;

import java.util.regex.Pattern;

public final class ProtocolPatterns {

    private ProtocolPatterns() {
    }

    public static final String NAME_GROUP = "(\\w*)";
    public static final String MESSAGE_GROUP = "(.*)";
    public static final String ARROW = "->";
    public static final String DOUBLE_COLON = ":";
    public static final String COMMA = ",";
    public static final String STEP = NAME_GROUP + ARROW + NAME_GROUP + DOUBLE_COLON + MESSAGE_GROUP;
    public static final Pattern STEP_PATTERN = Pattern.compile(STEP);
}
