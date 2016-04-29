package org.banlogic.inference;

import org.banlogic.inference.rules.InferMessageMeaningForPublicKeys;
import org.banlogic.inference.rules.InferMessageMeaningForSharedKeys;
import org.banlogic.inference.rules.InferenceRule;

import java.util.ArrayList;
import java.util.List;
import org.banlogic.inference.rules.InferComponentsForPublicKeys;
import org.banlogic.inference.rules.InferComponentsForSelfPublicKeys;
import org.banlogic.inference.rules.InferComponentsForSharedKeys;
import org.banlogic.inference.rules.InferJurisdiction;
import org.banlogic.inference.rules.InferMessageMeaningForSharedSecrets;
import org.banlogic.inference.rules.InferNonceVerification;

public final class InferenceSystem {

    private final List<InferenceRule> inferenceRuleList = new ArrayList<>();

    public InferenceSystem() {
        inferenceRuleList.add(new InferMessageMeaningForSharedKeys());
        inferenceRuleList.add(new InferMessageMeaningForPublicKeys());
        inferenceRuleList.add(new InferMessageMeaningForSharedSecrets());
        inferenceRuleList.add(new InferJurisdiction());
        inferenceRuleList.add(new InferNonceVerification());
        inferenceRuleList.add(new InferComponentsForPublicKeys());
        inferenceRuleList.add(new InferComponentsForSelfPublicKeys());
        inferenceRuleList.add(new InferComponentsForSharedKeys());
    }

    public List<String> inferFormulas(String firstFormula, String secondFormula) {
        List<String> newFormulas = new ArrayList<>();

        inferenceRuleList.forEach(rule -> newFormulas.addAll(rule.apply(firstFormula, secondFormula)));

        return newFormulas;
    }
}
