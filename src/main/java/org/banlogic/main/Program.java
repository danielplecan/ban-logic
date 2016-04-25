package org.banlogic.main;

import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.banlogic.file.FileReader;
import org.banlogic.model.ProtocolStep;
import org.banlogic.parser.ProtocolParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Alexandru on 4/23/2016.
 */
public class Program {

    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);

    public static void main(String... args) {
        BasicConfigurator.configure();

        List<String> protocolLines = FileReader.readLines("kerberos.txt");

        List<ProtocolStep> protocolSteps = ProtocolParser.parseProtocolSteps(protocolLines);
        List<String> protocolAssumptions = ProtocolParser.parseProtocolAssumptions(protocolLines);

        System.out.println(protocolSteps);
        System.out.println(protocolAssumptions);
    }
}
