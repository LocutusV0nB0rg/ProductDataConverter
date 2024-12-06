package com.smartheusys.eurochem.input;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class InputLineService {
    private final InputLineRepository inputLineRepository;

    @Inject
    public InputLineService(InputLineRepository inputLineRepository) {
        this.inputLineRepository = inputLineRepository;
    }

    public List<InputLine> getInputLines() {
        return inputLineRepository.getInputLines();
    }
}
