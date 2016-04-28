package org.banlogic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static org.banlogic.model.ProtocolPatterns.STEP_PATTERN;
import org.banlogic.model.ProtocolStep;

public final class ProtocolParser {

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
        protocolStep.setMessages(parseMessageee(stepMatcher.group(3)));

        return protocolStep;
    }

    public static List<String> parseMessage(String tobeParsed) {
        Pattern patternSimple = Pattern.compile("(\\{(.*)\\}_[\\w],)|(\\{(.*)\\}_[\\w])");
        Matcher matcher = patternSimple.matcher(tobeParsed);
        if (matcher.matches()) {
            String[] split = tobeParsed.split(",(?![^{]*})");
            return Arrays.asList(split);
        } else if (patternSimple.matcher(tobeParsed).matches()) {
            return Arrays.asList(new String[]{tobeParsed});
        }

        return Arrays.asList();
    }

    public static List<String> parseMessageee(String message) {
        Pattern pattern = Pattern.compile("(\\{(.+)\\}_inv\\(\\w+\\),)|(\\{(.+)\\}_inv\\(\\w+\\))|(\\{(.+)\\}_\\w+,)|(\\{(.+)\\}_\\w+)|(\\w+,)|(\\w+)");
        Matcher matcher = pattern.matcher(message);
        List<String> list = new ArrayList<>();

        while (matcher.find()) {
            String match = matcher.group(0);
            if (match.endsWith(",")) {
                match = match.substring(0, match.length() - 1);
            }
            list.add(match);
        }

        return list;
    }
}
