package com.smartheusys.eurochem.ui;

import com.smartheusys.eurochem.ProductDataConverter;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class PDCUI extends JFrame {
    public PDCUI(ProductDataConverter productDataConverter, Logger logger) {
        super("Product Data Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 180);

        ImageIcon img = new ImageIcon("ui/pdc_logo.png");
        Image image = img.getImage();
        setIconImage(image);

        add(new ControlButtonPanel(productDataConverter, logger));

        setVisible(true);
    }
}
