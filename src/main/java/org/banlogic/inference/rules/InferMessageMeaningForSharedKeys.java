package org.banlogic.inference.rules;

import org.banlogic.model.Operators;
import org.banlogic.parser.ProtocolParser;
import org.banlogic.util.SetUtil;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;
import static org.banlogic.inference.InferenceSystemUtils.generateAllFormulas;
import static org.banlogic.model.Operators.BELIEVES;
import static org.banlogic.model.Operators.KEY;
import static org.banlogic.model.Patterns.BELIEVES_PATTERN;
import static org.banlogic.model.Patterns.SEES_PATTERN;
import static org.banlogic.model.ProtocolPatterns.NAME_GROUP;

/**
 * Created by Alexandru on 4/28/2016.
 */
public class InferMessageMeaningForSharedKeys implements InferenceRule {

    @Override
    public List<String> apply(String firstFormula, String secondFormula) {
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
}