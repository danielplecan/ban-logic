package org.banlogic.inference;

import org.banlogic.inference.rules.InferMessageMeaningForPublicKeys;
import org.banlogic.inference.rules.InferMessageMeaningForSharedKeys;
import org.banlogic.inference.rules.InferenceRule;

import java.util.ArrayList;
import java.util.List;
import org.banlogic.inference.rules.InferMessageMeaningForSharedSecrets;

public final class InferenceSystem {

    private final List<InferenceRule> inferenceRuleList = new ArrayList<>();

    public InferenceSystem() {
        inferenceRuleList.add(new InferMessageMeaningForSharedKeys());
        inferenceRuleList.add(new InferMessageMeaningForPublicKeys());
        inferenceRuleList.add(new InferMessageMeaningForSharedSecrets());
    }

    public List<String> inferFormulas(String firstFormula, String secondFormula) {
        List<String> newFormulas = new ArrayList<>();

        inferenceRuleList.forEach(rule -> newFormulas.addAll(rule.apply(firstFormula, secondFormula)));

        return newFormulas;
    }
}
