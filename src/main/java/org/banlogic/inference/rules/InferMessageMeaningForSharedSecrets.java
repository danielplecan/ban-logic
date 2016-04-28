package org.banlogic.inference.rules;

import static java.lang.String.format;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import static org.banlogic.inference.InferenceSystemUtils.generateAllFormulas;
import org.banlogic.model.Operators;
import static org.banlogic.model.Operators.BELIEVES;
import static org.banlogic.model.Operators.SECRET;
import static org.banlogic.model.Patterns.BELIEVES_PATTERN;
import static org.banlogic.model.Patterns.SEES_PATTERN;
import static org.banlogic.model.ProtocolPatterns.NAME_GROUP;
import org.banlogic.parser.ProtocolParser;
import org.banlogic.util.SetUtil;

public class InferMessageMeaningForSharedSecrets implements InferenceRule {

    @Override
    public List<String> apply(String firstFormula, String secondFormula) {
        String firstPatternValue = "^" + NAME_GROUP + BELIEVES_PATTERN + NAME_GROUP + format(SECRET.getValue(), NAME_GROUP) + "\\1$";
        Pattern firstPattern = compile(firstPatternValue);
        Matcher firstMatcher = firstPattern.matcher(firstFormula);

        String firstPatternValueReversed = "^" + NAME_GROUP + BELIEVES_PATTERN + "\\1" + format(SECRET.getValue(), NAME_GROUP) + NAME_GROUP + "$";
        Pattern firstPatternReversed = compile(firstPatternValueReversed);
        Matcher firstMatcherReversed = firstPatternReversed.matcher(firstFormula);

        String principalP;
        String principalQ;
        String secret;

        if (!firstMatcher.matches()) {
            if (!firstMatcherReversed.matches()) {
                return Collections.EMPTY_LIST;
            } else {
                principalP = firstMatcherReversed.group(1);
                principalQ = firstMatcherReversed.group(3);
                secret = firstMatcherReversed.group(2);
            }
        } else {
            principalP = firstMatcher.group(1);
            principalQ = firstMatcher.group(2);
            secret = firstMatcher.group(3);
        }

        String secondPatternValue = "^" + principalP + SEES_PATTERN + "(<(.+)>_" + secret + ")$";
        Pattern secondPattern = compile(secondPatternValue);
        Matcher secondMatcher = secondPattern.matcher(secondFormula);

        if (!secondMatcher.matches() || ProtocolParser.parseMessageee(secondMatcher.group(1)).size() != 1) {
            return Collections.EMPTY_LIST;
        }

        String message = secondMatcher.group(2);

        return generateAllFormulas(principalP + BELIEVES.getValue() + principalQ + Operators.SAID.getValue(), SetUtil.generatePowerSet(ProtocolParser.parseMessageee(message)));
    }
}
