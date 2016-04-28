package org.banlogic.inference.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import org.banlogic.model.Operators;
import org.banlogic.model.Patterns;
import static org.banlogic.model.Patterns.BELIEVES_PATTERN;
import static org.banlogic.model.Patterns.FRESH_PATTERN;
import static org.banlogic.model.ProtocolPatterns.NAME_GROUP;

public class InferNonceVerification implements InferenceRule {

    @Override
    public List<String> apply(String firstFormula, String secondFormula) {
        String firstPatternValue = "^" + NAME_GROUP + BELIEVES_PATTERN + String.format(FRESH_PATTERN, "(.+)");
        Pattern firstPattern = compile(firstPatternValue);
        Matcher firstMatcher = firstPattern.matcher(firstFormula);

        if (!firstMatcher.matches()) {
            return Collections.EMPTY_LIST;
        }

        String principalP = firstMatcher.group(1);
        String message = firstMatcher.group(2);

        String secondPatternValue = "^" + principalP + BELIEVES_PATTERN + NAME_GROUP + Patterns.SAID_PATTERN + "(" + message + ")$";
        Pattern secondPattern = compile(secondPatternValue);
        Matcher secondMatcher = secondPattern.matcher(secondFormula);

        if (!secondMatcher.matches()) {
            return Collections.EMPTY_LIST;
        }

        String principalQ = secondMatcher.group(1);

        return Arrays.asList(principalP + Operators.BELIEVES.getValue() + principalQ + Operators.BELIEVES.getValue() + message);
    }
}
