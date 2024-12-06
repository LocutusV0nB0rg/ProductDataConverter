package com.smartheusys.eurochem;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.smartheusys.eurochem.input.InputLineExcelRepository;
import com.smartheusys.eurochem.input.InputLineRepository;
import com.smartheusys.eurochem.logs.LoggingModule;
import com.smartheusys.eurochem.output.OutputLineExcelRepository;
import com.smartheusys.eurochem.output.OutputLineRepository;
import org.apache.logging.log4j.Logger;

public class App {
    private static Injector injector;

    public static void main(String[] args) {
        System.out.println("Starting application in App class. Logger is not yet available.");

        injector = Guice.createInjector(
                new LoggingModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(InputLineRepository.class).to(InputLineExcelRepository.class);
                        bind(OutputLineRepository.class).to(OutputLineExcelRepository.class);
                    }
                }
        );

        System.out.printf("Application started in App class. Logger is available. Injector: %s%n", injector);
        Logger logger = injector.getInstance(Logger.class);
        logger.info("Welcome to the wonderful world of ProductDataConverter.");

        injector.getInstance(ProductDataConverter.class).startApplication(args);
    }
}