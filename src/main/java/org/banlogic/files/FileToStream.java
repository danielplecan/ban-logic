package org.banlogic.files;

import org.omg.SendingContext.RunTime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Alexandru on 4/23/2016.
 */
public class FileToStream {

    public static Stream<String> readFile(String filename){
        if (filename == null){
            throw new RuntimeException("What the actual f ? null filename...");
        }

        try(Stream<String> stream = Files.lines(Paths.get(filename))){
            return stream;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Stream.empty();
    }
}
