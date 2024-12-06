package com.smartheusys.eurochem.logs;

import com.google.inject.AbstractModule;
import org.apache.logging.log4j.Logger;

public class LoggingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Logger.class).toProvider(new LoggerProvider("PDC"));
    }
}
