package org.banlogic.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Alexandru on 4/23/2016.
 */
public class Program {

    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);

    public static void main(String... args) {
        BasicConfigurator.configure();
        //TODO init point
        if(args.length < 1){
            LOGGER.info("Input format: app.jar filename.txt");
            return;
        }
    }
}
