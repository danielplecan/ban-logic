package org.banlogic.inference;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.banlogic.model.Operators;
import org.banlogic.model.ProtocolStep;
import org.banlogic.util.SetUtil;

public final class InferenceSystem {

    private InferenceSystem() {
    }

    public static List<String> generateSeesFormulasFromProtocolStep(ProtocolStep protocolStep) {
        Set<Set<String>> messagesPowerSet = SetUtil.generatePowerSet(protocolStep.getMessages());

        Predicate<Set> emptySetPredicate = Set::isEmpty;

        return messagesPowerSet
                .stream()
                .filter(emptySetPredicate.negate())
                .map(messages -> generateSeesFormula(protocolStep.getReceiver(), messages))
                .collect(Collectors.toList());
    }

    private static String generateSeesFormula(String principal, Set<String> messages) {
        return principal + Operators.SEES.getValue() + messages.stream().collect(Collectors.joining(","));
    }

    public static List<String> inferFormulas(String firstFormula, String secondFormula) {
        return null;
    }
}
