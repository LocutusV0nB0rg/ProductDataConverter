package com.smartheusys.eurochem.output;

import java.util.List;

public interface OutputLineRepository {
    void saveOutputLines(List<OutputLine> outputLines);
}
