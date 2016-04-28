package org.banlogic.inference;

import org.banlogic.model.Operators;
import org.banlogic.model.ProtocolStep;
import org.banlogic.util.SetUtil;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Alexandru on 4/28/2016.
 */
public class InferenceSystemUtils {

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

}
