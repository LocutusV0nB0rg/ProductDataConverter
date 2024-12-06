package com.smartheusys.eurochem.logs;

import com.google.inject.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerProvider implements Provider<Logger> {

    private final String loggerName;

    public LoggerProvider(String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public Logger get() {
        return LogManager.getLogger(loggerName);
    }
}
