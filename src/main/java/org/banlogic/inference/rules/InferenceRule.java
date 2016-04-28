package org.banlogic.inference.rules;

import java.util.List;

/**
 * Created by Alexandru on 4/28/2016.
 */
public interface InferenceRule {
    List<String> apply(String firstFormula, String secondFormula);
}
