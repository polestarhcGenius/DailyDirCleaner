package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Main {
    private static final Logger logger = LoggerFactory.getLogger("Main");
    public static void main(String[] args){
        logger.info("Program started");
        new MainFrame("DirCleaner V 1.0");
    }
}