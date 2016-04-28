package org.banlogic.inference.rules;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import static org.banlogic.inference.InferenceSystemUtils.generateAllFormulas;
import static org.banlogic.model.Operators.BELIEVES;
import static org.banlogic.model.Patterns.BELIEVES_PATTERN;
import static org.banlogic.model.Patterns.CONTROLS_PATTERN;
import static org.banlogic.model.ProtocolPatterns.NAME_GROUP;
import static org.banlogic.parser.ProtocolParser.parseMessageee;
import static org.banlogic.util.SetUtil.generatePowerSet;

public class InferJurisdiction implements InferenceRule {
    
    @Override
    public List<String> apply(String firstFormula, String secondFormula) {
        String firstPatternValue = "^" + NAME_GROUP + BELIEVES_PATTERN + NAME_GROUP + CONTROLS_PATTERN + "(.+)";
        Pattern firstPattern = compile(firstPatternValue);
        Matcher firstMatcher = firstPattern.matcher(firstFormula);
        
        if (!firstMatcher.matches()) {
            return Collections.EMPTY_LIST;
        }
        
        String principalP = firstMatcher.group(1);
        String principalQ = firstMatcher.group(2);
        String message = firstMatcher.group(3);
        
        String secondPatternValue = "^" + principalP + BELIEVES_PATTERN + principalQ + BELIEVES_PATTERN + message;
        Pattern secondPattern = compile(secondPatternValue);
        Matcher secondMatcher = secondPattern.matcher(secondFormula);
        
        if (!secondMatcher.matches()) {
            return Collections.EMPTY_LIST;
        }
        
        return generateAllFormulas(principalP + BELIEVES.getValue(), generatePowerSet(parseMessageee(message)));
    }
}
