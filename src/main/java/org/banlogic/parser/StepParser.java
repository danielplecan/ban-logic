package org.banlogic.parser;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.banlogic.model.ProtocolStep;

public final class StepParser {

    private static final String PRINCIPAL_GROUP = "(\\w*)";
    private static final String MESSAGE_GROUP = "(.*)";
    private static final String ARROW = "->";
    private static final String DOUBLE_COLON = ":";
    private static final String COMMA = ",";

    private StepParser() {
    }

    public static List<ProtocolStep> parseProtocolSteps(List<String> stepLines) {
        Pattern stepPattern = Pattern.compile(PRINCIPAL_GROUP + ARROW + PRINCIPAL_GROUP + DOUBLE_COLON + MESSAGE_GROUP);
        Predicate<String> stepPredicate = stepPattern.asPredicate();

        boolean allMatch = stepLines.stream().allMatch(stepPredicate);

        if (allMatch) {
            return stepLines
                    .stream()
                    .map(stepPattern::matcher)
                    .filter(Matcher::matches)
                    .map(StepParser::buildProtocolStep)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Protocol description contains invalid steps");
        }
    }

    private static ProtocolStep buildProtocolStep(Matcher stepMatcher) {
        ProtocolStep protocolStep = new ProtocolStep();

        protocolStep.setSender(stepMatcher.group(1));
        protocolStep.setReceiver(stepMatcher.group(2));
        protocolStep.setMessage(Arrays.asList(stepMatcher.group(3).split(COMMA)));

        return protocolStep;
    }
}
