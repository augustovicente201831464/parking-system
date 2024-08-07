package com.cunoc.edu.gt.config;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Custom log filter to only log INFO and SEVERE messages.
 *
 * @Author: Augusto Vicente
 */
public class CustomLogFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        Level level = record.getLevel();
        return level == Level.INFO || level == Level.SEVERE || level == Level.WARNING;
    }
}