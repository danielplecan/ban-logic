package org.banlogic.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Alexandru on 4/23/2016.
 */
public final class FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    private FileReader() {
    }

    public static List<String> readLines(String filename) {
        Predicate<String> emptyPredicate = String::isEmpty;

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            return stream.map((line) -> line.replaceAll("\\s", ""))
                    .filter(emptyPredicate.negate())
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            LOGGER.warn("Error reading from file", exception);
        }

        return Collections.EMPTY_LIST;
    }
}
