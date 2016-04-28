package org.banlogic.inference;

import static java.lang.String.format;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import java.util.stream.Collectors;
import org.banlogic.model.Operators;
import static org.banlogic.model.Operators.BELIEVES;
import static org.banlogic.model.Operators.KEY;
import static org.banlogic.model.Patterns.BELIEVES_PATTERN;
import static org.banlogic.model.Patterns.INV_PATTERN;
import static org.banlogic.model.Patterns.PK_PATTERN;
import static org.banlogic.model.Patterns.SEES_PATTERN;
import static org.banlogic.model.ProtocolPatterns.NAME_GROUP;
import org.banlogic.model.ProtocolStep;
import org.banlogic.parser.ProtocolParser;
import org.banlogic.util.SetUtil;

public final class InferenceSystem {

    private InferenceSystem() {
    }

    public static List<String> generateSeesFormulasFromProtocolStep(ProtocolStep protocolStep) {
        Set<Set<String>> messagesPowerSet = SetUtil.generatePowerSet(protocolStep.getMessages());

        return generateAllFormulas(protocolStep.getReceiver() + Operators.SEES.getValue(), messagesPowerSet);
    }

    public static List<String> generateAllFormulas(String startOfFormula, Set<Set<String>> messagesPowerSet) {
        Predicate<Set> emptySetPredicate = Set::isEmpty;

        return messagesPowerSet
                .stream()
                .filter(emptySetPredicate.negate())
                .map(messages -> generateFormula(startOfFormula, messages))
                .collect(Collectors.toList());
    }

    public static String generateFormula(String startOfFormula, Set<String> messages) {
        return startOfFormula + messages.stream().collect(Collectors.joining(","));
    }

    public static List<String> inferFormulas(String firstFormula, String secondFormula) {
        List<String> newFormulas = inferMessageMeaningForSharedKeys(firstFormula, secondFormula);

        if (!newFormulas.isEmpty()) {
            return newFormulas;
        }

        return Collections.EMPTY_LIST;
    }

    public static List<String> inferMessageMeaningForSharedKeys(String firstFormula, String secondFormula) {
        String firstPatternValue = "^" + NAME_GROUP + BELIEVES_PATTERN + NAME_GROUP + format(KEY.getValue(), NAME_GROUP) + "\\1$";
        Pattern firstPattern = compile(firstPatternValue);
        Matcher firstMatcher = firstPattern.matcher(firstFormula);

        if (!firstMatcher.matches()) {
            return Collections.EMPTY_LIST;
        }

        String principalP = firstMatcher.group(1);
        String principalQ = firstMatcher.group(2);
        String key = firstMatcher.group(3);

        String secondPatternValue = "^" + principalP + SEES_PATTERN + "(\\{(.+)\\}_" + key + ")$";
        Pattern secondPattern = compile(secondPatternValue);
        Matcher secondMatcher = secondPattern.matcher(secondFormula);

        if (!secondMatcher.matches() || ProtocolParser.parseMessageee(secondMatcher.group(1)).size() != 1) {
            return Collections.EMPTY_LIST;
        }

        String message = secondMatcher.group(2);

        return generateAllFormulas(principalP + BELIEVES.getValue() + principalQ + Operators.SAID.getValue(), SetUtil.generatePowerSet(ProtocolParser.parseMessageee(message)));
    }

    public static List<String> inferMessageMeaningForPublicKeys(String firstFormula, String secondFormula) {
        String firstPatternValue = "^" + NAME_GROUP + BELIEVES_PATTERN + String.format(PK_PATTERN, NAME_GROUP, NAME_GROUP);
        Pattern firstPattern = compile(firstPatternValue);
        Matcher firstMatcher = firstPattern.matcher(firstFormula);

        if (!firstMatcher.matches()) {
            return Collections.EMPTY_LIST;
        }

        String principalP = firstMatcher.group(1);
        String principalQ = firstMatcher.group(2);
        String key = firstMatcher.group(3);

        String secondPatternValue = "^" + principalP + SEES_PATTERN + "(\\{(.+)\\}_" + String.format(INV_PATTERN, key) + ")$";
        Pattern secondPattern = compile(secondPatternValue);
        Matcher secondMatcher = secondPattern.matcher(secondFormula);

        if (!secondMatcher.matches() || ProtocolParser.parseMessageee(secondMatcher.group(1)).size() != 1) {
            return Collections.EMPTY_LIST;
        }

        String message = secondMatcher.group(2);

        return generateAllFormulas(principalP + BELIEVES.getValue() + principalQ + Operators.SAID.getValue(), SetUtil.generatePowerSet(ProtocolParser.parseMessageee(message)));
    }
}
