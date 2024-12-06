package com.smartheusys.eurochem.output;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor__ = @Inject)
public class OutputLineService {
    private final OutputLineRepository outputLineRepository;
    private final Logger logger;

    public void saveOutputLines(List<OutputLine> outputLines) {
        logger.debug("Saving {} output lines...", outputLines.size());

        outputLineRepository.saveOutputLines(outputLines);
    }
}
