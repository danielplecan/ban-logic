package org.banlogic.main;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.BasicConfigurator;
import org.banlogic.file.FileReader;
import org.banlogic.inference.InferenceSystem;
import org.banlogic.model.ProtocolStep;
import org.banlogic.parser.ProtocolParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.banlogic.inference.InferenceSystemUtils.generateSeesFormulasFromProtocolStep;

/**
 * Created by Alexandru on 4/23/2016.
 */
public class Program {

    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);

    public static void main(String... args) {
        BasicConfigurator.configure();

        List<String> protocolLines = FileReader.readLines("nspk.txt");
//        List<String> protocolLines = FileReader.readLines("andrew-secure-rpc.txt");
//        List<String> protocolLines = FileReader.readLines("ccitt.txt");
//        List<String> protocolLines = FileReader.readLines("kerberos.txt");

        Set<String> formulas = new LinkedHashSet<>();

        List<ProtocolStep> protocolSteps = ProtocolParser.parseProtocolSteps(protocolLines);
        formulas.addAll(ProtocolParser.parseProtocolAssumptions(protocolLines));

        protocolSteps.forEach((step) -> {
            formulas.addAll(generateSeesFormulasFromProtocolStep(step));
        });

        while (true) {
            int initialNoOfFormulas = formulas.size();
            InferenceSystem inferenceSystem = new InferenceSystem();
            List<String> inferedFormulas = new ArrayList<>();

            formulas.forEach((firstFormula) -> {
                formulas.forEach((secondFormula) -> {
                    inferedFormulas.addAll(inferenceSystem.inferFormulas(firstFormula, secondFormula));
                });
            });

            formulas.addAll(inferedFormulas);

            if (initialNoOfFormulas == formulas.size()) {
                break;
            }
        }

        formulas.forEach(formula -> System.out.println(formula));
    }
}
