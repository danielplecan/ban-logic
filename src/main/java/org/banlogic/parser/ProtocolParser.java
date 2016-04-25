package org.banlogic.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.banlogic.model.ProtocolStep;

public final class ProtocolParser {

    private static final String PRINCIPAL_GROUP = "(\\w*)";
    private static final String MESSAGE_GROUP = "(.*)";
    private static final String ARROW = "->";
    private static final String DOUBLE_COLON = ":";
    private static final String COMMA = ",";
    private static final String STEP = PRINCIPAL_GROUP + ARROW + PRINCIPAL_GROUP + DOUBLE_COLON + MESSAGE_GROUP;
    private static final Pattern STEP_PATTERN = Pattern.compile(STEP);

    private ProtocolParser() {
    }

    public static List<ProtocolStep> parseProtocolSteps(List<String> protocolLines) {
        return protocolLines
                .stream()
                .map(STEP_PATTERN::matcher)
                .filter(Matcher::matches)
                .map(ProtocolParser::buildProtocolStep)
                .collect(Collectors.toList());
    }

    public static List<String> parseProtocolAssumptions(List<String> protocolLines) {
        return protocolLines
                .stream()
                .filter(STEP_PATTERN.asPredicate().negate())
                .collect(Collectors.toList());
    }

    private static ProtocolStep buildProtocolStep(Matcher stepMatcher) {
        ProtocolStep protocolStep = new ProtocolStep();

        protocolStep.setSender(stepMatcher.group(1));
        protocolStep.setReceiver(stepMatcher.group(2));
        protocolStep.setMessage(Arrays.asList(stepMatcher.group(3).split(COMMA)));

        return protocolStep;
    }
}
