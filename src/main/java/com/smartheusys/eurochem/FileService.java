package com.smartheusys.eurochem;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Singleton
@RequiredArgsConstructor(onConstructor__ = @Inject)
public class FileService {
    private final Logger logger;

    @Getter
    @Setter
    private String inputFile;

    @Getter
    @Setter
    private String outputFile;


    public void deleteFileIfExists(String filename) {
        logger.debug("Deleting file if it exists: {}", filename);

        try {
            Files.delete(Paths.get(filename));
            logger.debug("File deleted: {}", filename);
        } catch (IOException e) {
            logger.debug("Failed to delete file: {}. Probably it just didn't exist.", filename);
        }
    }
}
