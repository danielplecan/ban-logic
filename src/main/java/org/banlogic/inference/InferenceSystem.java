package org.banlogic.inference;

import org.banlogic.inference.rules.InferMessageMeaningForSharedKeys;
import org.banlogic.inference.rules.InferenceRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class InferenceSystem {

    private List<InferenceRule> inferenceRuleList = new ArrayList<>();

    public InferenceSystem() {
        inferenceRuleList.add(new InferMessageMeaningForSharedKeys());
    }

    public List<String> inferFormulas(String firstFormula, String secondFormula) {
        List<String> newFormulas = new ArrayList<>();

        inferenceRuleList.forEach((rule) ->
                        newFormulas.addAll(rule.apply(firstFormula, secondFormula))
        );

        if (!newFormulas.isEmpty()) {
            return newFormulas;
        }

        return Collections.EMPTY_LIST;
    }
}
