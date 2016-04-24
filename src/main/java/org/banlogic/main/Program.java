package org.banlogic.main;

import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.banlogic.file.FileReader;
import org.banlogic.model.ProtocolStep;
import org.banlogic.parser.StepParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Alexandru on 4/23/2016.
 */
public class Program {

    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);

    public static void main(String... args) {
        BasicConfigurator.configure();

        List<String> lines = FileReader.readLines("kerberos.txt");

        List<ProtocolStep> protocolSteps = StepParser.parseProtocolSteps(lines);

        System.out.println(protocolSteps);
    }
}
